package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.thebrisingers.slotmachinegame.FocusManager
import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.spell.Target
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.data.SPIN_PRICE
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import fr.thebrisingers.slotmachinegame.machine.MachineState
import fr.thebrisingers.slotmachinegame.spellBar.SpellBarState
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

class GameScreen : KtxScreen, InputAdapter() {
    private lateinit var machineState: MachineState
    private lateinit var battleState: BattleState
    private lateinit var spellBarState: SpellBarState
    private lateinit var inventoryState: InventoryState

    private lateinit var gameRenderer: GameRenderer

    private lateinit var focusManager: FocusManager

    private lateinit var stage: Stage
    private var focusChangeSound: Sound? = null
    private var spellCastSound: Sound? = null
    private var backgroundMusic: Music? = null

    override fun show() {
        stage = Stage(ScreenViewport())

        battleState = BattleState()
        inventoryState = InventoryState()
        machineState = MachineState({ runes, coinsGained, healGained ->
            inventoryState.updateCounters(runes)
            inventoryState.addCoins(coinsGained)
            battleState.hero.heal(healGained)
        })
        spellBarState = SpellBarState()
        focusManager = FocusManager { spellBarState.spells }

        spellBarState.updateDescription(focusManager.current)

        gameRenderer = GameRenderer(stage, machineState, battleState, inventoryState, spellBarState)

        val multiplexer = InputMultiplexer()
        multiplexer.addProcessor(stage)
        multiplexer.addProcessor(this)
        Gdx.input.inputProcessor = multiplexer

        val soundFile = Gdx.files.internal("sounds/switch_focus.mp3")
        if (soundFile.exists()) focusChangeSound = Gdx.audio.newSound(soundFile)

        val spellSoundFile = Gdx.files.internal("sounds/throw_power.mp3")
        if (spellSoundFile.exists()) spellCastSound = Gdx.audio.newSound(spellSoundFile)

        val musicFile = Gdx.files.internal("sounds/background_music.mp3")
        if (musicFile.exists()) {
            backgroundMusic = Gdx.audio.newMusic(musicFile)
            backgroundMusic?.isLooping = true
            backgroundMusic?.volume = 0.1f
            backgroundMusic?.play()
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.12f, 0.12f, 0.18f, 1f)
        val currentFocus = focusManager.current
        val isSpinFocused = currentFocus is FocusTarget.Spin

        // On passe l'info au renderer global
        gameRenderer.render(delta, isSpinFocused, currentFocus)
    }

    override fun resize(width: Int, height: Int) {
        gameRenderer.resize(width, height)
    }

    override fun dispose() {
        gameRenderer.dispose()
        stage.disposeSafely()
        focusChangeSound?.disposeSafely()
        spellCastSound?.disposeSafely()
        backgroundMusic?.stop()
        backgroundMusic?.disposeSafely()
    }

    private var spaceHoldStartTime = 0L
    private val holdThresholdMs = 500L // seuil hold = 500ms

    override fun keyDown(keycode: Int): Boolean {
        if (battleState.phase != TurnPhase.PLAYER_TURN) return false

        if (keycode == Keys.SPACE) {
            spaceHoldStartTime = System.currentTimeMillis()
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        // Si on est en fin de partie et qu'on appuie sur R, on pourrait reset le jeu
        if ((battleState.phase == TurnPhase.VICTORY || battleState.phase == TurnPhase.GAME_OVER)
            && keycode == Keys.R) {
            show() // Relance l'initialisation du jeu
            return true
        }

        if (battleState.phase != TurnPhase.PLAYER_TURN) return false

        if (keycode == Keys.SPACE) {
            val duration = System.currentTimeMillis() - spaceHoldStartTime
            if (duration < holdThresholdMs) {
                changeFocus()
            } else {
                applyAction()
            }
        }
        return true
    }

    val isPress: Boolean
        get() = Gdx.input.isKeyPressed(Keys.SPACE)

    private fun changeFocus() {
        focusManager.next()
        spellBarState.updateDescription(focusManager.current)
        focusChangeSound?.play()
    }

    private fun applyAction() {
        when (val target = focusManager.current) {
            is FocusTarget.Spin -> {
                if (inventoryState.coins >= SPIN_PRICE) {
                    inventoryState.spendCoins(SPIN_PRICE)
                    gameRenderer.machineRenderer.triggerActivation()

                    // Déclencher les animations pour chaque monstre qui va attaquer
                    battleState.monsters.forEachIndexed { index, monster ->
                        if (monster.attackThisTurn) {
                            gameRenderer.battleRenderer.triggerMonsterAttack(index)
                        }
                    }

                    machineState.spin()
                    battleState.advanceMonsterTurn()
                }
            }
            is FocusTarget.Spell -> {
                val spell = spellBarState.spells[target.index]

                val aliveIndices = battleState.monsters.mapIndexedNotNull { index, m ->
                    if (m.isAlive) index else null
                }

                // --- CALCUL DES INDICES TOUCHÉS ---
                val hitIndices = mutableSetOf<Int>()
                spell.spellEffect.forEach { effect ->
                    when (effect.target) {
                        Target.FRONT -> if (aliveIndices.isNotEmpty()) hitIndices.add(aliveIndices.first())
                        Target.BACK -> if (aliveIndices.isNotEmpty()) hitIndices.add(aliveIndices.last())
                        Target.ALL -> hitIndices.addAll(aliveIndices)
                        Target.SELF -> { /* On ignore le héros pour l'anim de hit des monstres */ }
                    }
                }

                // Vérification des ressources
                if (inventoryState.canCastSpell(spell.cost)) {
                    // 1. Consommation
                    inventoryState.consumeResources(spell.cost)

                    // 2. Logique de combat
                    battleState.castSpell(spell)

                    // 3. Animation du héros
                   gameRenderer.battleRenderer.triggerCast(hitIndices.toList())
                    spellCastSound?.play(3f)
                } else {
                    gameRenderer.inventoryRenderer.triggerErrorFlash()

                    println("Pas assez de ressources pour ${spell.name}")
                }
            }
        }
    }
}

package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.thebrisingers.slotmachinegame.FocusManager
import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.spell.Target
import fr.thebrisingers.slotmachinegame.battle.BattleState
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

    override fun show() {
        stage = Stage(ScreenViewport())

        battleState = BattleState()
        inventoryState = InventoryState()
        machineState = MachineState({ inventoryState.updateCounters(it) })
        spellBarState = SpellBarState()
        focusManager = FocusManager { spellBarState.spells }

        spellBarState.updateDescription(focusManager.current)

        gameRenderer = GameRenderer(stage, machineState, battleState, inventoryState, spellBarState)

        val multiplexer = InputMultiplexer()
        multiplexer.addProcessor(stage)
        multiplexer.addProcessor(this)
        Gdx.input.inputProcessor = multiplexer
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
    }

    private var spaceHoldStartTime = 0L
    private val holdThresholdMs = 500L // seuil hold = 500ms

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            spaceHoldStartTime = System.currentTimeMillis()
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
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
    }

    private fun applyAction() {
        when (val target = focusManager.current) {
            is FocusTarget.Spin -> {
                gameRenderer.machineRenderer.triggerActivation()

                // Déclencher les animations pour chaque monstre qui va attaquer
                battleState.monsters.forEachIndexed { index, monster ->
                    if (monster.attackThisTurn) {
                        gameRenderer.battleRenderer.triggerMonsterAttack(index)
                    }
                }

                battleState.advanceMonsterTurn()
                machineState.spin()
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
                } else {
                    gameRenderer.inventoryRenderer.triggerErrorFlash()

                    println("Pas assez de ressources pour ${spell.name}")
                }
            }
        }
    }
}

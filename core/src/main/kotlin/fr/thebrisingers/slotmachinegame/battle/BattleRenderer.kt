package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.battle.init.InitHero
import fr.thebrisingers.slotmachinegame.battle.init.InitOrc
import fr.thebrisingers.slotmachinegame.battle.init.InitRogue
import fr.thebrisingers.slotmachinegame.battle.init.InitSkeleton
import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.caracter.Faction
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import ktx.app.clearScreen
import ktx.assets.disposeSafely

class BattleRenderer(
    private val battleState: BattleState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
    private val font: BitmapFont
) {
    private val combatIcon = Texture(Gdx.files.internal("combat.png"))
    private var currentAnimation: Animation<TextureRegion>
    private var stateTime = 0f

    // Mapping pour associer une Faction à son set d'animations
    private val monsterAnimations = mutableMapOf<Faction, Animation<TextureRegion>>()
    private val monsterStateTimes = FloatArray(3)
    private val monsterHitStateTimes = FloatArray(3)
    private val monsterDeathStateTimes = FloatArray(3)
    private val monsterIsDying = BooleanArray(3)
    private val monsterAttackStateTimes = FloatArray(3)
    private val monsterIsAttacking = BooleanArray(3)

    private val backgroud = Texture(Gdx.files.internal("combat_background.png"))

    private var lastWaveRendered = 1

    private val layout = GlyphLayout()

    var isAnimationDone = true
        private set

    // MAYBE
    //private var monsterOffsetList = ENEMY_Y_OFFSET.shuffled()

    init {
        // Initialisation du héros
        currentAnimation = InitHero.idleAnimation

        // Initialisation des monstres : on lie la Faction à l'animation Idle de l'objet correspondant
        // (On utilise les objets Init que tu as déjà créés)
        monsterAnimations[Faction.SKELETON] = InitSkeleton.idleAnimation
        monsterAnimations[Faction.ZOMBIE] = InitRogue.idleAnimation
        monsterAnimations[Faction.GOBLIN] = InitOrc.idleAnimation

        // Optionnel : décaler le temps de départ des monstres pour qu'ils ne bougent pas tous en même temps
        for (i in monsterHitStateTimes.indices) {
            monsterStateTimes[i] = (0..100).random() / 100f
            // Les monstres subissent
            monsterHitStateTimes[i] = 99f   // Initialisé à "fini"
            monsterDeathStateTimes[i] = 99f // Initialisé à "fini"
            monsterIsDying[i] = false
            // les monstres attaquent
            monsterAttackStateTimes[i] = 99f
            monsterIsAttacking[i] = false
        }
    }

    // 2. Fonction pour changer d'animation depuis l'extérieur (ex: quand on lance un sort)
    fun playAnimation(newAnim: Animation<TextureRegion>) {
        currentAnimation = newAnim
        stateTime = 0f // On recommence le chrono à zéro pour la nouvelle anim
    }

    fun triggerCast(targetIndices: List<Int>) {
        playAnimation(InitHero.attackAnimation)
        targetIndices.forEach { i ->
            if (i in monsterHitStateTimes.indices) {
                monsterHitStateTimes[i] = 0f
            }
        }
    }

    fun triggerMonsterAttack(index: Int) {
        if (index in monsterIsAttacking.indices) {
            monsterIsAttacking[index] = true
            monsterAttackStateTimes[index] = 0f
        }
    }

    fun render(delta: Float) {
        // On force le retour au blanc pour éviter les fuites de couleur de l'inventaire
        batch.color = Color.WHITE
        font.color = Color.WHITE

        // Si la vague a changé dans le state, on reset nos timers locaux
        if (battleState.waveNumber != lastWaveRendered) {
            lastWaveRendered = battleState.waveNumber
            for (i in monsterStateTimes.indices) {
                monsterStateTimes[i] = 0f
                monsterHitStateTimes[i] = 99f
                monsterDeathStateTimes[i] = 99f
                monsterIsDying[i] = false
            }
        }

        stateTime += delta
        for (i in monsterStateTimes.indices) {
            monsterStateTimes[i] += delta
            monsterHitStateTimes[i] += delta
            monsterDeathStateTimes[i] += delta
            monsterAttackStateTimes[i] += delta

            // Si l'attaque du monstre i est finie, on déclenche le hit du héros
            if (monsterIsAttacking[i]) {
                val attackAnim = getAttackAnim(battleState.monsters[i].faction)
                if (attackAnim.isAnimationFinished(monsterAttackStateTimes[i])) {
                    monsterIsAttacking[i] = false
                    // On joue l'animation de dégâts du héros (si elle existe dans InitHero)
                    playAnimation(InitHero.getHitAnimation)
                }
            }
        }

        // Logique de retour à l'IDLE pour le héros
        if (currentAnimation != InitHero.idleAnimation && currentAnimation.isAnimationFinished(stateTime)) {
            currentAnimation = InitHero.idleAnimation
            stateTime = 0f
        }

        // Mise à jour du flag d'animation terminée
        isAnimationDone = currentAnimation.isAnimationFinished(stateTime)

        drawCombatZone()       // 1. Fond et HP
        drawMonsterAnimations() // 2. Monstres
        drawHeroAnimation()    // 3. Héros (par dessus)

        if (battleState.phase == TurnPhase.VICTORY || battleState.phase == TurnPhase.GAME_OVER) {
            drawEndScreen()
        }
    }


    private fun drawMonsterAnimations() {
        batch.begin()
        battleState.monsters.forEachIndexed { i, monster ->
            val hitAnim = getHitAnim(monster.faction)
            val deathAnim = getDeathAnim(monster.faction)
            val attackAnim = getAttackAnim(monster.faction)

            // Détection du début de la mort
            if (!monster.isAlive && !monsterIsDying[i] && monsterDeathStateTimes[i] > 1f) {
                monsterIsDying[i] = true
                monsterDeathStateTimes[i] = 0f
            }

            // Déterminer quelle animation afficher
            val isHit = monsterHitStateTimes[i] < hitAnim.animationDuration
            val isDying = monsterIsDying[i] && !deathAnim.isAnimationFinished(monsterDeathStateTimes[i])
            val isAttacking = monsterIsAttacking[i]

            if (monster.isAlive || isDying || isHit) {
                val anim = when {
                    isDying -> deathAnim
                    isHit -> hitAnim
                    isAttacking -> attackAnim
                    else -> monsterAnimations[monster.faction]
                }

                val time = when {
                    isDying -> monsterDeathStateTimes[i]
                    isHit -> monsterHitStateTimes[i]
                    isAttacking -> monsterAttackStateTimes[i]
                    else -> monsterStateTimes[i]
                }

                if (anim != null) {
                    val currentFrame = anim.getKeyFrame(time)
                    val x = ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP)

                    // Optionnel : Petit flash rouge quand on prend un coup
                    if (isHit) batch.setColor(1f, 0.6f, 0.6f, 1f)

                    batch.draw(currentFrame, x, ENEMY_Y, ENEMY_W, ENEMY_H)
                    batch.setColor(Color.WHITE)
                }
            }
        }
        batch.end()
    }

    private fun drawEndScreen() {
        clearScreen(0f,0f,0f)
        // 1. Dessiner un voile sombre sur tout l'écran
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0f, 0f, 0f, 0.7f) // Noir transparent
        shapeRenderer.rect(0f, 0f, WORLD_W, WORLD_H)
        shapeRenderer.end()

        batch.begin()
        font.data.setScale(1.5f)

        val isVictory = battleState.phase == TurnPhase.VICTORY
        font.color = if (isVictory) Color.GOLD else Color.RED

        val message = if (isVictory) "VICTOIRE !" else "GAME OVER"

        val layout = GlyphLayout(font, message)
        font.draw(batch, message, (WORLD_W - layout.width) / 2, (WORLD_H + layout.height) / 2)

        font.data.setScale(0.6f)
        font.color = Color.WHITE
        val subMessage =
            if (isVictory) "Vous avez triomphé des $MAX_WAVES vagues" else "Le mage a succombé..."
        val subLayout = GlyphLayout(font, subMessage)
        font.draw(batch, subMessage, (WORLD_W - subLayout.width) / 2, (WORLD_H / 2) - 40f)

        batch.end()
        font.data.setScale(1f) // Reset
    }

    private fun getHitAnim(faction: Faction) = when(faction) {
        Faction.SKELETON -> InitSkeleton.getHitAnimation
        Faction.ZOMBIE -> InitRogue.getHitAnimation
        Faction.GOBLIN -> InitOrc.getHitAnimation
    }

    private fun getAttackAnim(faction: Faction) = when(faction) {
        Faction.SKELETON -> InitSkeleton.attackAnimation
        Faction.ZOMBIE -> InitRogue.attackAnimation
        Faction.GOBLIN -> InitOrc.attackAnimation
    }

    private fun getDeathAnim(faction: Faction) = when(faction) {
        Faction.SKELETON -> InitSkeleton.deathAnimation
        Faction.ZOMBIE -> InitRogue.deathAnimation
        Faction.GOBLIN -> InitOrc.deathAnimation
    }

    private fun drawHeroAnimation() {
        val currentFrame = currentAnimation.getKeyFrame(stateTime)

        batch.begin()
        // On dessine le hero à sa position MAGE_X, MAGE_Y
        // On l'agrandit un peu (ex: 64x64) car 32x32 c'est petit
        batch.draw(currentFrame, MAGE_X, MAGE_Y, MAGE_W, MAGE_H)
        batch.end()
    }

    private fun drawCombatZone() {

        batch.begin()
        batch.draw(backgroud, COMBAT_X, COMBAT_Y, COMBAT_W, COMBAT_H)
        batch.end()
        // 1. Dessin des formes (Fonds)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        // 2. Dessin des barres de vie
        val barWidth = 40f
        val barHeight = 6f

        // Barre HP Hero
        drawHealthBar(MAGE_X + (MAGE_W - barWidth) / 2f, MAGE_Y + MAGE_H + 10f,
            battleState.hero.health, battleState.hero.maxHealth, barWidth, barHeight)

        // Barres HP Monstres
        battleState.monsters.forEachIndexed { i, monster ->
            if (monster.isAlive) {
                val x = ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP) + (ENEMY_W - barWidth) / 2f
                drawHealthBar(x, ENEMY_Y + ENEMY_H + 10f,
                    monster.health, monster.maxHealth, barWidth, barHeight)
                // On dessine un petit cercle orange pour l'intention d'attaque
                val circleX = x + ENEMY_W - 10f
                val circleY = ENEMY_Y + ENEMY_H + 22f

                // Couleur changeante : rouge si attaque au prochain tour, sinon orange
                shapeRenderer.color = if (monster.turnsUntilNextAttack <= 1) Color.RED else Color.ORANGE
                shapeRenderer.circle(circleX, circleY, 3f)
            }
        }
        shapeRenderer.end()

        // 2. Séparateur
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.line(COMBAT_W, COMBAT_Y, COMBAT_W, COMBAT_Y + COMBAT_H)
        shapeRenderer.end()

        // 3. Dessin des textes (HP)
        batch.begin()
        font.color = Color.WHITE
        font.data.setScale(0.4f)

        // HP mage
        font.draw(batch, "${battleState.hero.health}/${battleState.hero.maxHealth}", MAGE_X + 10f, MAGE_Y + MAGE_H + 16f)

        // HP ennemis
        battleState.monsters.forEachIndexed { i, monster ->
            if (monster.isAlive) {
                val x = ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP)
                val circleX = x + ENEMY_W - 10f
                val circleY = ENEMY_Y + ENEMY_H + 22f

                // On affiche le nombre de tours à l'intérieur du cercle
                font.color = Color.WHITE
                // Ajustement pour centrer le chiffre dans le cercle
                font.draw(batch, "${monster.turnsUntilNextAttack}", circleX - 6f, circleY + 3f)
                batch.draw(combatIcon, circleX, circleY - 4f, 10f, 10f)

                font.draw(
                    batch,
                    "${monster.health}/${monster.maxHealth}",
                    ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP) + 10f,
                    ENEMY_Y + ENEMY_H + 16f
                )
            }
        }
        batch.end()
    }

    /**
     * Fonction utilitaire pour dessiner une barre de vie
     */
    private fun drawHealthBar(x: Float, y: Float, current: Int, max: Int, width: Float, height: Float) {
        // Fond de la barre (Gris foncé / Noir)
        shapeRenderer.color = Color.BLACK
        shapeRenderer.rect(x, y, width, height)

        // Calcul du remplissage
        val percent = current.toFloat() / max.toFloat()

        // Couleur : Vert si > 50%, Jaune si > 25%, Rouge sinon
        shapeRenderer.color = when {
            percent > 0.5f -> Color.GREEN
            percent > 0.25f -> Color.YELLOW
            else -> Color.RED
        }

        shapeRenderer.rect(x, y, width * percent, height)
    }

    fun renderEndScreen() {
        val phase = battleState.phase
        if (phase == TurnPhase.VICTORY || phase == TurnPhase.GAME_OVER) {
            // 1. Voile sombre
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = Color(0f, 0f, 0f, 0.75f)
            shapeRenderer.rect(0f, 0f, WORLD_W, WORLD_H)
            shapeRenderer.end()

            batch.begin()
            val isVictory = phase == TurnPhase.VICTORY

            // --- TITRE PRINCIPAL ---
            font.data.setScale(1.5f)
            font.color = if (isVictory) Color.GOLD else Color.RED
            val mainMessage = if (isVictory) "VICTOIRE !" else "GAME OVER"

            // Calcul du centrage précis pour le titre
            layout.setText(font, mainMessage)
            val mainX = (WORLD_W - layout.width) / 2
            val mainY = (WORLD_H + layout.height) / 2 + 20f
            font.draw(batch, mainMessage, mainX, mainY)

            // --- SOUS-TITRE ---
            font.data.setScale(0.6f)
            font.color = Color.WHITE
            val subMessage = if (isVictory) "Félicitations aventurier !\n\nAppuyez sur R pour rejouer" else "Appuyez sur R pour retenter"

            // Calcul du centrage précis pour le sous-titre
            layout.setText(font, subMessage)
            val subX = (WORLD_W - layout.width) / 2
            val subY = mainY - 50f // Placé sous le titre principal
            font.draw(batch, subMessage, subX, subY)

            batch.end()
            font.data.setScale(1f) // Reset indispensable
        }
    }

    fun dispose() {
        InitHero.dispose()
        InitOrc.dispose()
        InitRogue.dispose()
        InitSkeleton.dispose()
        combatIcon.disposeSafely()
    }
}

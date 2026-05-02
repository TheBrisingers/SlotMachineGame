package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.battle.init.InitHero
import fr.thebrisingers.slotmachinegame.battle.init.InitOrc
import fr.thebrisingers.slotmachinegame.battle.init.InitRogue
import fr.thebrisingers.slotmachinegame.battle.init.InitSkeleton
import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.caracter.Faction
import ktx.assets.disposeSafely

class BattleRenderer(
    private val battleState: BattleState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private var currentAnimation: Animation<TextureRegion>
    private var stateTime = 0f

    // Mapping pour associer une Faction à son set d'animations
    private val monsterAnimations = mutableMapOf<Faction, Animation<TextureRegion>>()
    private val monsterStateTimes = FloatArray(3)

    private val font = BitmapFont()

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
        for (i in monsterStateTimes.indices) {
            monsterStateTimes[i] = (0..100).random() / 100f
        }
    }

    // 2. Fonction pour changer d'animation depuis l'extérieur (ex: quand on lance un sort)
    fun playAnimation(newAnim: Animation<TextureRegion>) {
        currentAnimation = newAnim
        stateTime = 0f // On recommence le chrono à zéro pour la nouvelle anim
    }

    fun triggerCast() {
        playAnimation(InitHero.attackAnimation)
    }

    fun render(delta: Float) {
        // On force le retour au blanc pour éviter les fuites de couleur de l'inventaire
        batch.color = Color.WHITE
        font.color = Color.WHITE

        stateTime += delta
        for (i in monsterStateTimes.indices) {
            monsterStateTimes[i] += delta
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
    }


    private fun drawMonsterAnimations() {
        batch.begin()
        battleState.monsters.forEachIndexed { i, monster ->
            if (monster.isAlive) {
                // On récupère l'animation correspondant à la faction du monstre
                val anim = monsterAnimations[monster.faction]
                val time = monsterStateTimes[i]

                if (anim != null) {
                    val currentFrame = anim.getKeyFrame(time)
                    val x = ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP)

                    batch.draw(
                        currentFrame,
                        x, ENEMY_Y,
                        ENEMY_W, ENEMY_H
                    )
                }
            }
        }
        batch.end()
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
        // 1. Dessin des formes (Fonds)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        // Fond de la zone de combat
        shapeRenderer.color = Color(0.08f, 0.08f, 0.14f, 1f)
        shapeRenderer.rect(COMBAT_X, COMBAT_Y, COMBAT_W, COMBAT_H)

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

    fun dispose() {
        font.disposeSafely()
        InitHero.dispose()
        InitOrc.dispose()
        InitRogue.dispose()
        InitSkeleton.dispose()
    }
}

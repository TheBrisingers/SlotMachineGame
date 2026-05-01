package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.*
import ktx.assets.disposeSafely

class BattleRenderer(
    private val battleState: BattleState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val idleAnimation: Animation<TextureRegion>
    private val attackAnimation: Animation<TextureRegion>
    private val getHitAnimation: Animation<TextureRegion>
    private val deathAnimation: Animation<TextureRegion>

    private var currentAnimation: Animation<TextureRegion>

    private var stateTime = 0f // Le chrono de l'animation

    private val font = BitmapFont()

    var isAnimationDone = true
        private set

    // MAYBE
    //private var monsterOffsetList = ENEMY_Y_OFFSET.shuffled()

    // 2. Fonction pour changer d'animation depuis l'extérieur (ex: quand on lance un sort)
    fun playAnimation(newAnim: Animation<TextureRegion>) {
        currentAnimation = newAnim
        stateTime = 0f // On recommence le chrono à zéro pour la nouvelle anim
    }

    fun render(delta: Float) {
        stateTime += delta

        // 3. Logique de retour à l'IDLE
        // Si l'animation actuelle n'est pas l'idle ET qu'elle est terminée
        if (currentAnimation != idleAnimation && currentAnimation.isAnimationFinished(stateTime)) {
            currentAnimation = idleAnimation
            stateTime = 0f
        }

        isAnimationDone = false

        drawCombatZone()
        drawHeroAnimation()
        isAnimationDone = true
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
        // Fond de la zone
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0.08f, 0.08f, 0.14f, 1f)
        shapeRenderer.rect(COMBAT_X, COMBAT_Y, COMBAT_W, COMBAT_H)
        shapeRenderer.end()

        // Séparateur vertical
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.line(COMBAT_W, COMBAT_Y, COMBAT_W, COMBAT_Y + COMBAT_H)
        shapeRenderer.end()

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        // Ennemis (alignés à gauche dans la zone)
        battleState.monsters.forEachIndexed { i, monster ->
            shapeRenderer.color = Color.WHITE
            shapeRenderer.rect(
                ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP),
                ENEMY_Y /*+ monsterOffsetList[i]*/,
                ENEMY_W,
                ENEMY_H
            )
        }
        shapeRenderer.end()

        batch.begin()
        // HP mage
        font.draw(batch, "HP: ${battleState.hero.health}", MAGE_X, MAGE_Y + MAGE_H)

        // HP bars ennemis
        battleState.monsters.forEachIndexed { i, monster ->
            font.draw(
                batch,
                "${monster.health}/${monster.maxHealth}",
                ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP),
                ENEMY_Y + ENEMY_H, /*+ monsterOffsetList[i]*/
            )
        }
        batch.end()
    }

    fun dispose() {
        font.disposeSafely()
        playerIdleSheet.disposeSafely()
        playerDeathSheet.disposeSafely()
        playerGetItSheet.disposeSafely()
        playerAttackSheet.disposeSafely()
    }

    // Hero Sheets
    private val playerIdleSheet = Texture(Gdx.files.internal("hero/Player_StandBy.png"))
    private val playerDeathSheet = Texture(Gdx.files.internal("hero/Player_Death.png"))
    private val playerGetItSheet = Texture(Gdx.files.internal("hero/Player_get_hit.png"))
    private val playerAttackSheet = Texture(Gdx.files.internal("hero/Player_attack.png"))

    init {
        // IDLE
        val idleFrames = TextureRegion.split(playerIdleSheet, 24, 24)[0]
        idleAnimation = Animation(0.1f, *idleFrames)
        idleAnimation.playMode = Animation.PlayMode.LOOP

        // ATTAQUE (Cast)
        val attackFrames = TextureRegion.split(playerAttackSheet, 24, 24)[0]
        attackAnimation = Animation(0.08f, *attackFrames) // Un peu plus rapide
        attackAnimation.playMode = Animation.PlayMode.NORMAL

        // GET HIT
        val hitFrames = TextureRegion.split(playerGetItSheet, 24, 24)[0]
        getHitAnimation = Animation(0.1f, *hitFrames)
        getHitAnimation.playMode = Animation.PlayMode.NORMAL

        // DEATH
        val deathFrames = TextureRegion.split(playerDeathSheet, 24, 24)[0]
        deathAnimation = Animation(0.15f, *deathFrames)
        deathAnimation.playMode = Animation.PlayMode.NORMAL

        // Par défaut
        currentAnimation = idleAnimation
    }
}

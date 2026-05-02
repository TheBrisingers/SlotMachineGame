package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.COMBAT_W
import fr.thebrisingers.slotmachinegame.data.SPELLS_H
import fr.thebrisingers.slotmachinegame.data.WORLD_W
import ktx.assets.disposeSafely

class MachineRenderer(
    private val machineState: MachineState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val textureFocused = Texture(Gdx.files.internal("lever/lever_focused.png"))
    private val textureActivation = Texture(Gdx.files.internal("lever/lever_activated.png")) // Nouveau fichier

    private val focusAnimation: Animation<TextureRegion>
    private val activationAnimation: Animation<TextureRegion>
    private val idleRegion: TextureRegion

    private var stateTime = 0f
    private var isActivating = false

    init {
        // On découpe la feuille (ex: frames de 32x32, à ajuster selon ton PNG)
        val frames = TextureRegion.split(textureFocused, 32, 98)
        val animationFrames = frames[0]

        idleRegion = animationFrames[0]
        focusAnimation = Animation(0.1f, *animationFrames)
        focusAnimation.playMode = Animation.PlayMode.LOOP

        val activationFrames = TextureRegion.split(textureActivation, 32, 95)[0]
        activationAnimation = Animation(0.05f, *activationFrames)
        activationAnimation.playMode = Animation.PlayMode.NORMAL
    }

    // Appelé par GameScreen quand on appuie sur Espace
    fun triggerActivation() {
        isActivating = true
        stateTime = 0f
    }

    fun render(delta: Float, isFocused: Boolean) {
        val buttonWidth = 30f
        val buttonHeight = 90f
        val gap = 15f
        val posX = WORLD_W - buttonWidth - gap
        val posY = SPELLS_H + 45f

        stateTime += delta

        batch.begin()
        val frame = when {
            // 1. Priorité à l'animation d'activation (le clic)
            isActivating -> {
                if (activationAnimation.isAnimationFinished(stateTime)) {
                    isActivating = false
                    stateTime = 0f
                    activationAnimation.getKeyFrame(stateTime)
                } else {
                    activationAnimation.getKeyFrame(stateTime)
                }
            }
            // 2. Si on a le focus, on joue l'anim de brillance
            isFocused -> focusAnimation.getKeyFrame(stateTime)
            // 3. Sinon, repos
            else -> {
                stateTime = 0f
                idleRegion
            }
        }

        batch.draw(frame, posX, posY, buttonWidth, buttonHeight)
        batch.end()
    }

    fun dispose() {
        textureFocused.disposeSafely()
        textureActivation.disposeSafely()
    }
}

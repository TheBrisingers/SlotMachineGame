package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.PANEL_X
import fr.thebrisingers.slotmachinegame.data.PANEL_Y
import fr.thebrisingers.slotmachinegame.data.SPELLS_H
import fr.thebrisingers.slotmachinegame.data.WORLD_W
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import ktx.assets.disposeSafely
import kotlin.math.roundToInt

class MachineRenderer(
    private val machineState: MachineState,
    private val inventoryState: InventoryState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val textureFocused = Texture(Gdx.files.internal("lever/lever_focused.png"))
    private val textureActivation = Texture(Gdx.files.internal("lever/lever_activated.png")) // Nouveau fichier
    private val wheelBackground = Texture(Gdx.files.internal("wheel/roue_a_sous.png")) // Nouveau fichier
    private val background = Texture(Gdx.files.internal("wheel/background.png")) // Nouveau fichier
    private val oneSlot = Texture(Gdx.files.internal("wheel/slot machine-sheet.png")) // Nouveau fichier
    private val waterSlot = Texture(Gdx.files.internal("wheel/water_slot.png")) // Nouveau fichier
    private val threeSlots = Texture(Gdx.files.internal("wheel/3 slot machinet.png")) // Nouveau fichier

    private val earthRune = Texture(Gdx.files.internal("runes/earth.png"))
    private val fireRune = Texture(Gdx.files.internal("runes/fire.png"))
    private val waterRune = Texture(Gdx.files.internal("runes/water.png"))
    private val windRune = Texture(Gdx.files.internal("runes/wind.png"))

    val frameDuration = 0.01f


    fun slotAnimation(texture: Texture, initialFrame: Int): Animation<TextureRegion> = run {
        val allFrames = TextureRegion.split(texture, 150, 200)[0]
        // On réordonne le tableau pour commencer à 'initialFrame'
        val rotatedFrames = Array(allFrames.size) { i ->
            allFrames[(i + initialFrame) % allFrames.size]
        }
        Animation(frameDuration, *rotatedFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }

    val decalage = 17

    val animationSlot0 = slotAnimation(oneSlot,0)
    val animationSlot1 = slotAnimation(oneSlot,decalage * 1)
    val animationSlot2 = slotAnimation(oneSlot,decalage * 2)
    val animationSlot3 = slotAnimation(oneSlot,decalage * 3)
    val threeSlotsAnimation: Animation<TextureRegion> = run {
        val attackFrames = TextureRegion.split(threeSlots, 150, 200)[0]
        Animation(0.08f, *attackFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }
    private val focusAnimation: Animation<TextureRegion>
    private val activationAnimation: Animation<TextureRegion>
    private val idleRegion: TextureRegion

    private var stateTimeLever = 0f
    private var stateTimeWheel = 0f
    private var isActivating = false

    private val font = BitmapFont()

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
        stateTimeLever = 0f
    }

    fun render(delta: Float, isFocused: Boolean) {
        val coinsValue = inventoryState.coins

        font.color = Color.WHITE

        val buttonWidth = 30f
        val buttonHeight = 90f
        val gap = 15f
        val posX = WORLD_W - buttonWidth - gap
        val posY = SPELLS_H + 45f

        stateTimeLever += delta
        stateTimeWheel += delta

        val runePosDelta = -137 / 68
        val totalDelta = (((runePosDelta * stateTimeLever)/ frameDuration % 137 +1) /2).roundToInt()*2

        println(totalDelta)
        batch.begin()

        // Dessin du fond de la roue
        batch.draw(wheelBackground, PANEL_X, PANEL_Y, 240f, 180f)

        // Dessin des slots avec leur propre stateTime
        val currentOneSlotFrame1 = animationSlot0.getKeyFrame(stateTimeLever)
        val currentOneSlotFrame2 = animationSlot1.getKeyFrame(stateTimeLever)
        val currentOneSlotFrame3 = animationSlot2.getKeyFrame(stateTimeLever)
        val currentOneSlotFrame4 = animationSlot3.getKeyFrame(stateTimeLever)
        val decalageX = -56f
        val decalageY = 36

        batch.draw(currentOneSlotFrame1, PANEL_X + decalageX, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame2, PANEL_X + decalageX, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame3, PANEL_X + decalageX, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame4, PANEL_X + decalageX, PANEL_Y + decalageY, 150f, 200f)

        batch.draw(currentOneSlotFrame1, PANEL_X + decalageX + 57f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame2, PANEL_X + decalageX + 57f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame3, PANEL_X + decalageX + 57f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame4, PANEL_X + decalageX + 57f, PANEL_Y + decalageY, 150f, 200f)

        batch.draw(currentOneSlotFrame1, PANEL_X + decalageX + 57f + 56f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame2, PANEL_X + decalageX + 57f + 56f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame3, PANEL_X + decalageX + 57f + 56f, PANEL_Y + decalageY, 150f, 200f)
        batch.draw(currentOneSlotFrame4, PANEL_X + decalageX + 57f + 56f, PANEL_Y + decalageY, 150f, 200f)

        // Obtenez la frame actuelle de l'animation threeSlotsAnimation
        val currentThreeSlotsFrame = threeSlotsAnimation.getKeyFrame(stateTimeWheel)
        //batch.draw(currentThreeSlotsFrame, PANEL_X, PANEL_Y, 150f, 200f)

        batch.draw(earthRune, PANEL_X + 40f, PANEL_Y + 137 + totalDelta, 32f, 32f)
        batch.draw(earthRune, PANEL_X + 40f, PANEL_Y + 0, 32f, 32f)

        batch.end()

        batch.begin()
        batch.draw(background, PANEL_X, PANEL_Y, 240f, 180f)
        batch.end()

        batch.begin()

        val frame = when {
            // 1. Priorité à l'animation d'activation (le clic)
            isActivating -> {
                if (activationAnimation.isAnimationFinished(stateTimeLever)) {
                    isActivating = false
                    stateTimeLever = 0f
                    activationAnimation.getKeyFrame(stateTimeLever)
                } else {
                    activationAnimation.getKeyFrame(stateTimeLever)
                }
            }
            // 2. Si on a le focus, on joue l'anim de brillance
            isFocused -> focusAnimation.getKeyFrame(stateTimeLever)
            // 3. Sinon, repos
            else -> {
                stateTimeLever = 0f
                idleRegion
            }
        }

        batch.draw(frame, posX, posY, buttonWidth, buttonHeight)
        font.draw(batch, "$coinsValue", posX, posY + 3f)

        batch.end()

    }

    fun dispose() {
        textureFocused.disposeSafely()
        textureActivation.disposeSafely()
        font.disposeSafely()
    }
}

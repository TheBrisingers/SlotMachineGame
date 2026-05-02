package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import ktx.assets.disposeSafely

class MachineRenderer(
    private val machineState: MachineState,
    private val inventoryState: InventoryState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
    private val font: BitmapFont,
) {
    private val frameCount = 68
    private val visibleRows = 3
    private val renderRows = 4
    private val framePhase = frameCount / visibleRows  // 22
    private val spinFramesPerSecond = 300f
    private val cyclesBeforeStop = 1
    private val reelStopDelays = listOf(1.2f, 2.2f, 3.2f)

    private val symbolDisplayW = 150f
    private val symbolDisplayH = 200f

    val decalageX = -56f
    val decalageY = 32f
    private val rowOffsetX = 57f
    private val rowSpacingY = -11f
    private val maxSpinSymbols = 20

    val coinX = PANEL_X + PANEL_W / 2 - 8f
    val coinY = WORLD_H - 25f

    private val textureFocused = Texture(Gdx.files.internal("lever/lever_focused.png"))
    private val textureActivation = Texture(Gdx.files.internal("lever/lever_activated.png"))
    private val wheelBackground = Texture(Gdx.files.internal("wheel/roue_a_sous.png"))
    private val background = Texture(Gdx.files.internal("wheel/background.png"))
    private val simpleCoin = Texture(Gdx.files.internal("runes/simple_coin.png"))

    private val symbolTextures: Map<Symbol, Texture> = mapOf(
        Symbol.WATER to Texture(Gdx.files.internal("wheel/water_slot.png")),
        Symbol.FIRE to Texture(Gdx.files.internal("wheel/fire_slot.png")),
        Symbol.EARTH to Texture(Gdx.files.internal("wheel/slot machine-earth.png")),
        Symbol.WIND to Texture(Gdx.files.internal("wheel/wind_slot.png")),
        Symbol.SIMPLE_COIN to Texture(Gdx.files.internal("wheel/slot machine-Coin.png")),
        Symbol.MULTIPLE_COIN to Texture(Gdx.files.internal("wheel/slot machine-TripleCoin.png")),
        Symbol.COIN_BAG to Texture(Gdx.files.internal("wheel/slot machine-bag_coin.png")),
        Symbol.JOKER to Texture(Gdx.files.internal("wheel/slot machine-Joker.png")),
        Symbol.HEAL to Texture(Gdx.files.internal("wheel/slot machine-heart.png")),
    )

    private val symbolFrames: Map<Symbol, Array<TextureRegion>> = symbolTextures.mapValues { (_, tex) ->
        TextureRegion.split(tex, 150, 200)[0]
    }

    private val focusAnimation: Animation<TextureRegion>
    private val activationAnimation: Animation<TextureRegion>
    private val idleRegion: TextureRegion
    private var stateTimeLever = 0f
    private var isActivating = false

    val isAnimationDone: Boolean
        get() = !isActivating && reelStates.all { it.stopped }

    var onSymbolTick: (() -> Unit)? = null

    data class ReelState(
        var globalFrame: Int = 0,
        var spinTime: Float = 0f,
        val spinDuration: Float,
        var isSpinning: Boolean = false,
        var stopped: Boolean = true,
        var accumulator: Float = 0f,
    )

    private var reelStates: List<ReelState> = reelStopDelays.map { delay ->
        ReelState(spinDuration = delay, stopped = true)
    }


    init {
        val leverFrames = TextureRegion.split(textureFocused, 32, 98)[0]
        idleRegion = leverFrames[0]
        focusAnimation = Animation(0.1f, *leverFrames).apply {
            playMode = Animation.PlayMode.LOOP
        }
        val activationFrames = TextureRegion.split(textureActivation, 32, 95)[0]
        activationAnimation = Animation(0.05f, *activationFrames).apply {
            playMode = Animation.PlayMode.NORMAL
        }
    }

    fun triggerActivation() {
        isActivating = true
        stateTimeLever = 0f

        val limitedWheels = listOf(
            machineState.machine.wheelOne,
            machineState.machine.wheelTwo,
            machineState.machine.wheelThree,
        ).map { getLimitedWheel(it) }

        val sequentialDelay = listOf(0f, 0.8f, 1.6f)

        reelStates = limitedWheels.mapIndexed { index, wheel ->
            val totalFrames = wheel.size * frameCount
            val duration = (totalFrames * cyclesBeforeStop) / spinFramesPerSecond + sequentialDelay[index]
            ReelState(
                globalFrame = 0,
                spinTime = 0f,
                spinDuration = duration,
                isSpinning = true,
                stopped = false,
                accumulator = 0f,
            )
        }
    }

    private fun getLimitedWheel(wheel: List<Symbol>): List<Symbol> =
        if (wheel.size > maxSpinSymbols) wheel.take(maxSpinSymbols) else wheel
    fun render(delta: Float, isFocused: Boolean) {
        stateTimeLever += delta

        val wheels = listOf(
            machineState.machine.wheelOne,
            machineState.machine.wheelTwo,
            machineState.machine.wheelThree,
        ).map { getLimitedWheel(it) }

        reelStates.forEachIndexed { index, reel ->
            if (reel.isSpinning) {
                reel.spinTime += delta
                reel.accumulator += delta * spinFramesPerSecond
                val framesToAdvance = reel.accumulator.toInt()
                val symbolsBefore = reel.globalFrame / frameCount
                reel.globalFrame += framesToAdvance
                reel.accumulator -= framesToAdvance
                val symbolsAfter = reel.globalFrame / frameCount
                repeat(symbolsAfter - symbolsBefore) { onSymbolTick?.invoke() }

                if (reel.spinTime >= reel.spinDuration) {
                    val totalFrames = wheels[index].size * frameCount
                    val currentFrameInSymbol = reel.globalFrame % frameCount
                    val framesToEndOfSymbol = if (currentFrameInSymbol == 0) 0
                    else frameCount - currentFrameInSymbol
                    reel.globalFrame += framesToEndOfSymbol
                    reel.globalFrame = ((reel.globalFrame / totalFrames) + 1) * totalFrames
                    reel.isSpinning = false
                    reel.stopped = true
                }
            }
        }

        batch.begin()
        batch.draw(wheelBackground, PANEL_X, PANEL_Y, 240f, 180f)
        batch.end()

        drawSymbols(wheels)

        batch.begin()
        batch.draw(background, PANEL_X, PANEL_Y, 240f, 180f)

        val leverFrame = when {
            isActivating -> {
                if (activationAnimation.isAnimationFinished(stateTimeLever)) {
                    isActivating = false
                    stateTimeLever = 0f
                    activationAnimation.getKeyFrame(0f)
                } else {
                    activationAnimation.getKeyFrame(stateTimeLever)
                }
            }
            isFocused -> focusAnimation.getKeyFrame(stateTimeLever)
            else -> {
                stateTimeLever = 0f
                idleRegion
            }
        }

        val buttonWidth = 30f
        val buttonHeight = 90f
        val gap = 15f
        batch.draw(leverFrame, WORLD_W - buttonWidth - gap, SPELLS_H + 45f, buttonWidth, buttonHeight)
        font.color = Color.WHITE
        font.draw(batch, "${inventoryState.coins}", PANEL_X + PANEL_W / 2 - 8f, WORLD_H - 22f)

        font.data.setScale(0.4f) // Texte assez petit
        // On le place 10 pixels sous le levier (posY - 10f)
        val priceText = "$SPIN_PRICE"
        font.draw(batch, priceText, WORLD_W - buttonWidth - gap + 10f, SPELLS_H + 45f - 5f)

        // On dessine l'icône de la pièce juste à côté du chiffre
        // On ajuste la position Y (-15f) pour que l'icône soit alignée avec le texte
        batch.draw(simpleCoin, WORLD_W - buttonWidth - gap + 14f, SPELLS_H + 45f - 14f, 12f, 12f)


        font.data.setScale(1f)
        batch.end()
    }

    private fun drawSymbols(wheels: List<List<Symbol>>) {
        batch.begin()

        wheels.forEachIndexed { wheelIndex, wheel ->
            val posX = PANEL_X + decalageX + wheelIndex * rowOffsetX
            val reel = reelStates[wheelIndex]

            for (row in 0 until renderRows) {
                val symbolIndex: Int
                val frameIndex: Int

                if (reel.stopped) {
                    symbolIndex = row.coerceAtMost(visibleRows - 1)
                    frameIndex = (row * framePhase).mod(frameCount)
                } else {
                    val totalFrames = wheel.size * frameCount
                    val rowOffset = row * (frameCount + framePhase)
                    val rowGlobalFrame = (reel.globalFrame + rowOffset).mod(totalFrames)
                    symbolIndex = rowGlobalFrame / frameCount
                    frameIndex = rowGlobalFrame % frameCount
                }

                val symbol = wheel[symbolIndex.coerceIn(0, wheel.size - 1)]
                val frames = symbolFrames[symbol] ?: continue
                val posY = PANEL_Y + decalageY + (renderRows - 1 - row) * rowSpacingY

                font.color = Color.WHITE
                font.data.setScale(0.4f) // Texte assez petit
                // On le place 10 pixels sous le levier (posY - 10f)
                val priceText = "$SPIN_PRICE"
                font.draw(batch, priceText, posX + 10f, posY - 5f)

                // On dessine l'icône de la pièce juste à côté du chiffre
                // On ajuste la position Y (-15f) pour que l'icône soit alignée avec le texte
                batch.draw(simpleCoin, posX + 14f, posY - 14f, 12f, 12f)


                font.data.setScale(1f)
                batch.draw(frames[frameIndex], posX, posY, symbolDisplayW, symbolDisplayH)
            }
        }

        batch.end()
    }

    fun dispose() {
        textureFocused.disposeSafely()
        textureActivation.disposeSafely()
        wheelBackground.disposeSafely()
        background.disposeSafely()
        symbolTextures.values.forEach { it.disposeSafely() }
    }
}

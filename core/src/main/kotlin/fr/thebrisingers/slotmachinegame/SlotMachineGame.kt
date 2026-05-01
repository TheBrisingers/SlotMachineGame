package fr.thebrisingers.slotmachinegame

import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.async.KtxAsync
import ktx.graphics.use

class SlotMachineGame : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}

class FirstScreen : KtxScreen, InputAdapter() {
    private val stage = Stage(FitViewport(1280f, 720f))

    override fun show() {
        Gdx.input.inputProcessor = this
    }

    override fun render(delta: Float) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        batch.use {
            it.draw(image, 100f, 160f)
        }
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
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

    }

    private fun applyAction() {

    }
}

package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.assets.disposeSafely

class MachineUI(val onSpin: () -> Unit) {
    val stage = Stage(ScreenViewport())
    private var textureNormal = Texture(Gdx.files.internal("logo.png"))

    init {
        val drawableNormal = TextureRegionDrawable(TextureRegion(textureNormal))
        val imageButton = ImageButton(ImageButton.ImageButtonStyle().apply {
            up = drawableNormal
        }).apply {
            setSize(250f, 50f)
            setPosition(200f, 20f)
        }

        imageButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                onSpin() // On prévient le contrôleur qu'on a cliqué
            }
        })

        stage.addActor(imageButton)
        Gdx.input.inputProcessor = stage
    }

    fun draw(delta: Float) {
        stage.act(delta)
        stage.draw()
    }

    fun dispose() {
        textureNormal.disposeSafely()
        stage.disposeSafely()
    }
}

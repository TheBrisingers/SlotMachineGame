package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import fr.thebrisingers.slotmachinegame.data.COMBAT_W
import fr.thebrisingers.slotmachinegame.data.SPELLS_H
import ktx.actors.onClick
import ktx.assets.disposeSafely

class MachineUI(
    val stage: Stage,
    val onSpin: () -> Unit
) {
    private var textureNormal = Texture(Gdx.files.internal("logo.png"))

    init {
        val drawableNormal = TextureRegionDrawable(TextureRegion(textureNormal))
        val imageButton = ImageButton(ImageButton.ImageButtonStyle().apply {
            up = drawableNormal
        }).apply {
            setSize(250f, 50f)
            setPosition( COMBAT_W, 20f + SPELLS_H)
        }

        imageButton.onClick { onSpin() }

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

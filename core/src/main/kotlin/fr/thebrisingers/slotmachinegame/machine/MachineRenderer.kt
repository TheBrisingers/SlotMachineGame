package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.COMBAT_W
import fr.thebrisingers.slotmachinegame.data.SPELLS_H
import ktx.assets.disposeSafely

class MachineRenderer(
    private val machineState: MachineState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val font = BitmapFont()

    fun render() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.SKY
        shapeRenderer.end()
    }

    fun dispose() {
        font.disposeSafely()
    }
}

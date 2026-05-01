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
        machineState.counters.forEach { count ->
            shapeRenderer.rect(count.zone.x + COMBAT_W, count.zone.y + SPELLS_H, count.zone.width, count.zone.height)
        }
        shapeRenderer.end()

        batch.begin()
        for (c in machineState.counters) {
            font.color = Color.YELLOW
            font.draw(batch, c.title, c.zone.x + COMBAT_W, c.zone.y + c.zone.height + 20f + SPELLS_H)
            font.color = Color.WHITE
            font.draw(batch, c.value.toString(), c.zone.x + 45f + COMBAT_W, c.zone.y + 35f + SPELLS_H)
        }
        batch.end()
    }

    fun dispose() {
        font.disposeSafely()
    }
}

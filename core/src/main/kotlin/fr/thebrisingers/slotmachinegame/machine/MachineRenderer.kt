package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import ktx.app.clearScreen
import ktx.assets.disposeSafely

class MachineRenderer {
    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val font = BitmapFont()

    fun render(counters: List<SymbolRect>) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.SKY
        counters.forEach { count ->
            shapeRenderer.rect(count.zone.x, count.zone.y, count.zone.width, count.zone.height)
        }
        shapeRenderer.end()

        batch.begin()
        for (c in counters) {
            font.color = Color.YELLOW
            font.draw(batch, c.title, c.zone.x, c.zone.y + c.zone.height + 20f)
            font.color = Color.WHITE
            font.draw(batch, c.value.toString(), c.zone.x + 45f, c.zone.y + 35f)
        }
        batch.end()
    }

    fun dispose() {
        batch.disposeSafely()
        shapeRenderer.disposeSafely()
        font.disposeSafely()
    }
}

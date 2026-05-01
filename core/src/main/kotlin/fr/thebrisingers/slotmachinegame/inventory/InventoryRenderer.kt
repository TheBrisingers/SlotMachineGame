package fr.thebrisingers.slotmachinegame.inventory

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import ktx.assets.disposeSafely

class InventoryRenderer(
    private val inventoryState: InventoryState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val font = BitmapFont()

    // On charge les textures dans une Map
    private val textures = mapOf(
        Symbol.FIRE.name to Texture(Gdx.files.internal("runes/fire.png")),
        Symbol.WATER.name to Texture(Gdx.files.internal("runes/water.png")),
        Symbol.EARTH.name to Texture(Gdx.files.internal("runes/earth.png")),
        Symbol.WIND.name to Texture(Gdx.files.internal("runes/wind.png"))
    )

    fun render() {
        drawInventoryZone()
    }

    private fun drawInventoryZone() {
        batch.begin()
        inventoryState.counters.forEach { counter ->
            val texture = textures[counter.title]

            if (texture != null) {
                // 1. Dessiner le sprite
                batch.draw(texture, counter.zone.x, counter.zone.y, counter.zone.width, counter.zone.height)

                // 2. Dessiner la valeur (le chiffre) juste à côté ou en dessous
                font.color = Color.WHITE
                // On affiche le score à droite de l'icône
                font.draw(batch, counter.value.toString(), counter.zone.x + counter.zone.width - 10f, counter.zone.y + 5f)
            }
        }
                batch.end()
    }

    fun dispose() {
        font.disposeSafely()
    }
}

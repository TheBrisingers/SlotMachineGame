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
    private val font: BitmapFont
) {
    private var errorFlashTimer = 0f

    // On charge les textures dans une Map
    private val textures = mapOf(
        Symbol.FIRE.name to Texture(Gdx.files.internal("runes/fire.png")),
        Symbol.WATER.name to Texture(Gdx.files.internal("runes/water.png")),
        Symbol.EARTH.name to Texture(Gdx.files.internal("runes/earth.png")),
        Symbol.WIND.name to Texture(Gdx.files.internal("runes/wind.png"))
    )

    fun render(delta: Float) {
        if (errorFlashTimer > 0) {
            errorFlashTimer -= delta
        } else {
            inventoryState.missingResources.clear()
        }

        drawInventoryZone()
    }

    fun triggerErrorFlash() {
        errorFlashTimer = 0.5f // Le flash dure 0.5 secondes
    }

    private fun drawInventoryZone() {
        batch.begin()
        inventoryState.counters.forEach { counter ->
            val texture = textures[counter.title]
            val isMissing = inventoryState.missingResources.contains(counter.title)

            if (texture != null) {
                // Si la ressource manque, on teinte le sprite et le texte
                if (isMissing && errorFlashTimer > 0f) {
                    batch.color = Color(1f, 0.3f, 0.3f, 1f)
                    font.color = Color(1f, 0.5f, 0.5f, 1f)
                } else {
                    batch.color = Color.WHITE
                    font.color = Color.WHITE
                }

                // 1. Dessiner le sprite
                batch.draw(texture, counter.zone.x, counter.zone.y, counter.zone.width, counter.zone.height)

                // 2. Dessiner la valeur (le chiffre) juste à côté ou en dessous
                font.color = Color.WHITE
                // On affiche le score à droite de l'icône
                font.draw(batch, counter.value.toString(), counter.zone.x + counter.zone.width - 10f, counter.zone.y + 5f)
            }
        }
        batch.color = Color.WHITE
        font.color = Color.WHITE
        batch.end()
    }

    fun dispose() {
        textures.values.forEach { it.disposeSafely() }
    }
}

package fr.thebrisingers.slotmachinegame.spellBar

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.*
import ktx.assets.disposeSafely

class SpellBarRenderer(
    private val spellBarState: SpellBarState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val font = BitmapFont()

    var isAnimationDone = true
        private set


    fun render() {
        isAnimationDone = false

        drawSpellsZone()
        isAnimationDone = true
    }

    private fun drawSpellsZone() {
        // Fond bande du bas
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0.06f, 0.06f, 0.12f, 1f)
        shapeRenderer.rect(SPELLS_X, SPELLS_Y, SPELLS_W, SPELLS_H)
        shapeRenderer.end()

        // Séparateur horizontal
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.line(0f, SPELLS_H, WORLD_W, SPELLS_H)
        shapeRenderer.end()

        // Les sorts sont gérés par le Stage (UI) — voir BattleUI
    }

    fun dispose() {
        font.disposeSafely()
    }
}

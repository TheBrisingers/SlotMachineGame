package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.*
import ktx.assets.disposeSafely

class BattleRenderer(
    private val world: BattleState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val font = BitmapFont()

    var isAnimationDone = true
        private set


    fun render() {
        isAnimationDone = false

        drawCombatZone()
        drawSpellsZone()
        isAnimationDone = true
    }

    private fun drawCombatZone() {
        // Fond de la zone
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color(0.08f, 0.08f, 0.14f, 1f)
        shapeRenderer.rect(COMBAT_X, COMBAT_Y, COMBAT_W, COMBAT_H)
        shapeRenderer.end()

        // Séparateur vertical
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.DARK_GRAY
        shapeRenderer.line(COMBAT_W, COMBAT_Y, COMBAT_W, COMBAT_Y + COMBAT_H)
        shapeRenderer.end()

        // Mage (à droite dans sa zone)
        val mageX = COMBAT_X + COMBAT_W - 80f
        val mageY = COMBAT_Y + 40f
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.CYAN
        shapeRenderer.rect(mageX, mageY, 44f, 56f)

        // Ennemis (alignés à gauche dans la zone)
        world.monsters.forEachIndexed { i, monster ->
            if (!monster.isAlive) return@forEachIndexed
            val ex = COMBAT_X + 40f + i * 90f
            val ey = COMBAT_Y + 40f
            shapeRenderer.color = Color.WHITE
            shapeRenderer.rect(ex, ey, 40f, 52f)
        }
        shapeRenderer.end()

        // HP bars ennemis
        batch.begin()
        world.monsters.forEachIndexed { i, enemy ->
            val ex = COMBAT_X + 40f + i * 90f
            val ey = COMBAT_Y + 40f + 58f
            font.draw(batch, "${enemy.health}/${enemy.maxHealth}", ex, ey)
        }
        // HP mage
        font.draw(batch, "HP: ${world.hero.health}", mageX - 10f, mageY + 70f)
        batch.end()
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

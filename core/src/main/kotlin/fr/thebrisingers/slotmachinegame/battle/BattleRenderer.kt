package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.thebrisingers.slotmachinegame.data.*

class BattleRenderer(private val world: BattleWorld) {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_W, WORLD_H, camera)
    private val batch = SpriteBatch()
    private val shapes = ShapeRenderer()
    private val font = BitmapFont()

    var isAnimationDone = true
        private set

    init {
        // Force une première mise à jour avec la taille réelle de la fenêtre
        viewport.update(Gdx.graphics.width, Gdx.graphics.height, true)
    }

    fun render(delta: Float) {
        isAnimationDone = false
        ScreenUtils.clear(0.12f, 0.12f, 0.18f, 1f)
        camera.update()
        shapes.projectionMatrix = camera.combined
        batch.projectionMatrix = camera.combined

        drawCombatZone()
        drawPanelZone()
        drawSpellsZone()
        isAnimationDone = true
    }

    private fun drawCombatZone() {

        // Fond de la zone
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color(0.08f, 0.08f, 0.14f, 1f)
        shapes.rect(COMBAT_X, COMBAT_Y, COMBAT_W, COMBAT_H)
        shapes.end()

        // Séparateur vertical
        shapes.begin(ShapeRenderer.ShapeType.Line)
        shapes.color = Color.DARK_GRAY
        shapes.line(COMBAT_W, COMBAT_Y, COMBAT_W, COMBAT_Y + COMBAT_H)
        shapes.end()

        // Mage (à droite dans sa zone)
        val mageX = COMBAT_X + COMBAT_W - 80f
        val mageY = COMBAT_Y + 40f
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color.CYAN
        shapes.rect(mageX, mageY, 44f, 56f)

        // Ennemis (alignés à gauche dans la zone)
        world.monsters.forEachIndexed { i, monster ->
            if (monster.isAlive) return@forEachIndexed
            val ex = COMBAT_X + 40f + i * 90f
            val ey = COMBAT_Y + 40f
            shapes.color = Color.WHITE
            shapes.rect(ex, ey, 40f, 52f)
        }
        shapes.end()

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

    private fun drawPanelZone() {
        /*// Fond panneau droit
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color(0.10f, 0.10f, 0.16f, 1f)
        shapes.rect(PANEL_X, PANEL_Y, PANEL_W, PANEL_H)
        shapes.end()

        // Contenu libre — log des événements, stats, etc.
        batch.begin()
        var logY = PANEL_Y + PANEL_H - 20f
        font.draw(batch, "Journal de combat", PANEL_X + 12f, logY)
        world.lastEvents.forEach { event ->
            logY -= 20f
            val line = when (event) {
                is BattleEvent.SpellHit -> "${event.spell.name} → ${event.damage} dégâts"
                is BattleEvent.EnemyAttack -> "Ennemi ${event.enemyId} attaque → ${event.damage}"
            }
            font.draw(batch, line, PANEL_X + 12f, logY)
        }
        batch.end()*/
    }

    private fun drawSpellsZone() {
        // Fond bande du bas
        shapes.begin(ShapeRenderer.ShapeType.Filled)
        shapes.color = Color(0.06f, 0.06f, 0.12f, 1f)
        shapes.rect(SPELLS_X, SPELLS_Y, SPELLS_W, SPELLS_H)
        shapes.end()

        // Séparateur horizontal
        shapes.begin(ShapeRenderer.ShapeType.Line)
        shapes.color = Color.DARK_GRAY
        shapes.line(0f, SPELLS_H, WORLD_W, SPELLS_H)
        shapes.end()

        // Les sorts sont gérés par le Stage (UI) — voir BattleUI
    }

    fun resize(width: Int, height: Int) = viewport.update(width, height, true)
    fun dispose() {
        batch.dispose()
        shapes.dispose()
        font.dispose()
    }
}

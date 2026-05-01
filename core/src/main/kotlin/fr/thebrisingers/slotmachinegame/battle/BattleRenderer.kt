package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.*
import ktx.assets.disposeSafely

class BattleRenderer(
    private val battleState: BattleState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
) {
    private val font = BitmapFont()

    var isAnimationDone = true
        private set

    // MAYBE
    //private var monsterOffsetList = ENEMY_Y_OFFSET.shuffled()

    fun render() {
        isAnimationDone = false

        drawCombatZone()
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.CYAN
        shapeRenderer.rect(MAGE_X, MAGE_Y, MAGE_W, MAGE_H)


        // Ennemis (alignés à gauche dans la zone)
        battleState.monsters.forEachIndexed { i, monster ->
            shapeRenderer.color = Color.WHITE
            shapeRenderer.rect(
                ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP),
                ENEMY_Y /*+ monsterOffsetList[i]*/,
                ENEMY_W,
                ENEMY_H
            )
        }
        shapeRenderer.end()

        batch.begin()
        // HP mage
        font.draw(batch, "HP: ${battleState.hero.health}", MAGE_X, MAGE_Y + MAGE_H)

        // HP bars ennemis
        battleState.monsters.forEachIndexed { i, monster ->
            font.draw(
                batch,
                "${monster.health}/${monster.maxHealth}",
                ENEMY_START_X + i * (ENEMY_W + ENEMY_GAP),
                ENEMY_Y + ENEMY_H, /*+ monsterOffsetList[i]*/
            )
        }
        batch.end()
    }

    fun dispose() {
        font.disposeSafely()
    }
}

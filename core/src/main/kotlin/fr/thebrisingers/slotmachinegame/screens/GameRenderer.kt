package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.thebrisingers.slotmachinegame.battle.BattleRenderer
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.data.WORLD_H
import fr.thebrisingers.slotmachinegame.data.WORLD_W
import fr.thebrisingers.slotmachinegame.inventory.InventoryRenderer
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import fr.thebrisingers.slotmachinegame.machine.MachineRenderer
import fr.thebrisingers.slotmachinegame.machine.MachineState
import ktx.assets.disposeSafely

class GameRenderer(
    private val stage: Stage,
    machineState: MachineState,
    battleState: BattleState,
    inventoryState: InventoryState
) {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_W, WORLD_H, camera)

    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()

    val machineRenderer = MachineRenderer(machineState, batch, shapeRenderer)
    val battleRenderer = BattleRenderer(battleState, batch, shapeRenderer)
    val inventoryRenderer = InventoryRenderer(inventoryState, batch, shapeRenderer)

    init {
        // Force une première mise à jour avec la taille réelle de la fenêtre
        viewport.update(Gdx.graphics.width, Gdx.graphics.height, true)
    }

    fun render(delta: Float) {
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        camera.update()
        machineRenderer.render()
        battleRenderer.render(delta)
        inventoryRenderer.render()
        stage.act(delta)
        stage.draw()
    }

    fun dispose() {
        batch.disposeSafely()
        shapeRenderer.disposeSafely()
        machineRenderer.dispose()
        battleRenderer.dispose()
        inventoryRenderer.dispose()
    }

    fun resize(width: Int, height: Int) = viewport.update(width, height, true)


}

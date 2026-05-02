package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeType
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.battle.BattleRenderer
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.data.WORLD_H
import fr.thebrisingers.slotmachinegame.data.WORLD_W
import fr.thebrisingers.slotmachinegame.inventory.InventoryRenderer
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import fr.thebrisingers.slotmachinegame.machine.MachineRenderer
import fr.thebrisingers.slotmachinegame.machine.MachineState
import fr.thebrisingers.slotmachinegame.spellBar.SpellBarRenderer
import fr.thebrisingers.slotmachinegame.spellBar.SpellBarState
import ktx.assets.disposeSafely

class GameRenderer(
    private val stage: Stage,
    machineState: MachineState,
    battleState: BattleState,
    inventoryState: InventoryState,
    spellBarState: SpellBarState,
) {
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_W, WORLD_H, camera)

    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val mainFont: BitmapFont = createFont("fonts/Good_Days.otf", 16)

    val machineRenderer = MachineRenderer(machineState, inventoryState, batch, shapeRenderer, mainFont)
    val battleRenderer = BattleRenderer(battleState, batch, shapeRenderer, mainFont )
    val inventoryRenderer = InventoryRenderer(inventoryState, batch, shapeRenderer, mainFont)
    val spellBarRenderer = SpellBarRenderer(spellBarState, batch, shapeRenderer, mainFont)

    init {
        // Force une première mise à jour avec la taille réelle de la fenêtre
        viewport.update(Gdx.graphics.width, Gdx.graphics.height, true)
    }

    private fun createFont(path: String, size: Int): BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal(path))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = size
        // Optionnel : pour le pixel art, on désactive le filtrage linéaire
        parameter.magFilter = Texture.TextureFilter.Nearest
        parameter.minFilter = Texture.TextureFilter.Nearest

        val font = generator.generateFont(parameter)
        generator.dispose() // Important de libérer le générateur
        return font
    }

    fun render(delta: Float, isLeverFocused: Boolean, currentFocus: FocusTarget) {
        batch.projectionMatrix = camera.combined
        shapeRenderer.projectionMatrix = camera.combined

        camera.update()
        machineRenderer.render(delta, isLeverFocused)
        battleRenderer.render(delta)
        inventoryRenderer.render(delta)
        spellBarRenderer.render(currentFocus)

        stage.act(delta)
        stage.draw()

        battleRenderer.renderEndScreen()
    }

    fun dispose() {
        batch.disposeSafely()
        shapeRenderer.disposeSafely()
        machineRenderer.dispose()
        battleRenderer.dispose()
        inventoryRenderer.dispose()
        mainFont.disposeSafely()
        spellBarRenderer.dispose()
    }

    fun resize(width: Int, height: Int) = viewport.update(width, height, true)


}

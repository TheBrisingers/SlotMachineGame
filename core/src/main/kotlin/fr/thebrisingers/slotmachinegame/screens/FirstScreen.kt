package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.thebrisingers.slotmachinegame.data.WHEEL_SYMBOL_PROPORTION
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import fr.thebrisingers.slotmachinegame.data.toRandomWheelValue
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely

class FirstScreen : KtxScreen {
    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val font = BitmapFont()
    private val stage = Stage(ScreenViewport())

    private var textureNormal: Texture? = null
    private var texturePressed: Texture? = null

    private val machine = Machine(
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue()
    )

    private val counters = listOf(
        SymbolRect(Symbol.FIRE.toString(), Rectangle(50f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WATER.toString(), Rectangle(200f, 100f, 100f, 60f)),
        SymbolRect(Symbol.EARTH.toString(), Rectangle(350f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WIND.toString(), Rectangle(500f, 100f, 100f, 60f))
    )

    init {
        setupUI()
    }

    private fun setupUI() {
        textureNormal = Texture(Gdx.files.internal("logo.png"))

        val drawableNormal = TextureRegionDrawable(TextureRegion(textureNormal))

        val imageButtonStyle = ImageButton.ImageButtonStyle().apply {
            up = drawableNormal
        }

        val imageButton = ImageButton(imageButtonStyle).apply {
            setSize(250f, 50f)
            setPosition(200f, 20f)
        }

        imageButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                machine.shuffleWheels()
                machine.getIncomes().forEach { updateCounters(it) }

                println(machine.spinResult)
                println(machine.getIncomes())
            }
        })

        stage.addActor(imageButton)
        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
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

        stage.act(delta)
        stage.draw()
    }

    private fun updateCounters(income: Pair<Symbol, Int>?) {
        income?.let { (symbol, gain) ->
            counters.find { it.title == symbol.name }?.let {
                it.value += gain
            }
        }
    }

    override fun dispose() {
        batch.disposeSafely()
        shapeRenderer.disposeSafely()
        font.disposeSafely()
        textureNormal.disposeSafely()
        texturePressed.disposeSafely()
        stage.disposeSafely()
    }
}

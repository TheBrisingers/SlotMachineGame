package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import ktx.app.clearScreen
import ktx.assets.disposeSafely

class MachineRenderer {
    private val batch = SpriteBatch()
    private val shapeRenderer = ShapeRenderer()
    private val font = BitmapFont()
    private val spriteSheet = Texture(Gdx.files.internal("monseigneur_le_vendeur.png"))
    private val animation: Animation<TextureRegion>

    init {
        val frames = TextureRegion.split(spriteSheet, 64, 64)
        val animationFrames = frames[0] // Récupère la première ligne

        // 0.1f est la vitesse (10 images par seconde)
        animation = Animation(0.1f, *animationFrames)
        animation.playMode = Animation.PlayMode.LOOP
    }
    fun render(counters: List<SymbolRect>, stateTime: Float, isSpinning: Boolean) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = Color.SKY
        counters.forEach { count ->
            shapeRenderer.rect(count.zone.x, count.zone.y, count.zone.width, count.zone.height)
        }
        shapeRenderer.end()

        batch.begin()

        if (isSpinning) {
            val currentFrame = animation.getKeyFrame(stateTime)
            batch.draw(currentFrame, 250f, 300f, 128f, 128f) // Dessine l'anim
        }

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
        spriteSheet.disposeSafely()
    }
}

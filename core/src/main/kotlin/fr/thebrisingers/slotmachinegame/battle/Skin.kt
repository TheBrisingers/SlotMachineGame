package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

// Skin minimal fonctionnel sans fichier externe
fun createSkin(): Skin {
    val skin = Skin()

    // Texture blanche 1x1 pour les fonds de boutons
    val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888).apply {
        setColor(Color.WHITE)
        fill()
    }
    skin.add("white", Texture(pixmap))
    pixmap.dispose()

    val font = BitmapFont()
    skin.add("default-font", font)

    val btnStyle = TextButton.TextButtonStyle().apply {
        up       = skin.newDrawable("white", Color.DARK_GRAY)
        down     = skin.newDrawable("white", Color.GRAY)
        over     = skin.newDrawable("white", Color(0.3f, 0.3f, 0.3f, 1f))
        disabled = skin.newDrawable("white", Color(0.15f, 0.15f, 0.15f, 1f))
        fontColor = Color.WHITE
        this.font = font
    }
    skin.add("default", btnStyle)

    return skin
}

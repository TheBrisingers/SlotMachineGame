package fr.thebrisingers.slotmachinegame.spellBar

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.spell.SpellCost
import ktx.assets.disposeSafely

class SpellBarRenderer(
    private val spellBarState: SpellBarState,
    private val batch: SpriteBatch,
    private val shapeRenderer: ShapeRenderer,
    private val font: BitmapFont
) {
    private val paperBackground = Texture(Gdx.files.internal("paper_background2.png"))

    // Single element books
    private val fireBook = Texture(Gdx.files.internal("spellBooks/fire_book.png"))
    private val waterBook = Texture(Gdx.files.internal("spellBooks/water_book.png"))
    private val earthBook = Texture(Gdx.files.internal("spellBooks/earth_book.png"))
    private val windBook = Texture(Gdx.files.internal("spellBooks/wind_book.png"))

    // Combined element books
    private val lightningBook = Texture(Gdx.files.internal("spellBooks/lightning_book.png")) // Fire + Air
    private val magmaBook = Texture(Gdx.files.internal("spellBooks/magma_book.png"))       // Fire + Earth
    private val iceBook = Texture(Gdx.files.internal("spellBooks/ice_book.png"))           // Water + Air
    private val fairyBook = Texture(Gdx.files.internal("spellBooks/fairy_book.png"))       // Earth + Water
    private val lightBook = Texture(Gdx.files.internal("spellBooks/light_book.png"))       // Air + Earth
    private val darkBook = Texture(Gdx.files.internal("spellBooks/dark_book.png"))         // Water + Fire

    // Cost symbols
    private val fireSymbol = Texture(Gdx.files.internal("runes/fire.png"))
    private val waterSymbol = Texture(Gdx.files.internal("runes/water.png"))
    private val earthSymbol = Texture(Gdx.files.internal("runes/earth.png"))
    private val windSymbol = Texture(Gdx.files.internal("runes/wind.png"))
    private val healIcon = Texture(Gdx.files.internal("runes/heart.png"))
    private val simpleCoinIcon = Texture(Gdx.files.internal("runes/simple_coin.png"))
    private val multiCoinIcon = Texture(Gdx.files.internal("runes/triple_coin.png"))
    private val bagIcon = Texture(Gdx.files.internal("runes/coin_bag.png"))
    private val jokerIcon = Texture(Gdx.files.internal("runes/joker.png"))
    private val background = Texture(Gdx.files.internal("background_fix.png"))

    // Constants for card layout
    private val cardPadding = 5f
    private val cardVerticalContentPadding = 5f // Padding for top and bottom of the card content

    private val costValueFontScale = 0.5f // Adjusted font for cost values
    private val titleFontScale = 0.5f // Adjusted font for title

    private val glyphLayout = GlyphLayout() // For measuring text

    // --- Fixed card width calculation ---
    private val MAX_SPELLS_ON_LINE = 5
    private val minSpacingBetweenCards = 10f // Fixed spacing between cards, used for overall layout

    // Calculate cardWidth based on SPELLS_W and max spells
    private val cardWidth: Float = (SPELLS_W - (MAX_SPELLS_ON_LINE + 1) * minSpacingBetweenCards) / MAX_SPELLS_ON_LINE

    // --- Adjust internal element sizes based on the new fixed cardWidth ---
    private val mainBookIconSize: Float = 16f // Max 32f, but fit within card
    private val costIconSize: Float = 16f // Max 32f, but fit 4 icons, increased slightly

    // Recalculate cardHeight based on adjusted sizes
    private val titleLineHeight: Float
    private val titleAreaHeight: Float
    private val costValueLineHeight: Float
    private val costAreaHeight: Float

    init {
        font.data.setScale(titleFontScale)
        titleLineHeight = font.lineHeight
        titleAreaHeight = titleLineHeight * 2 // Allow for two lines of title

        font.data.setScale(costValueFontScale)
        costValueLineHeight = font.lineHeight
        costAreaHeight = costIconSize + costValueLineHeight // Cost icon + its value below
        font.data.setScale(1f) // Reset font scale to default
    }

    private val cardHeight = mainBookIconSize + titleAreaHeight + costAreaHeight + cardPadding


    fun render(currentFocus: FocusTarget) {
        batch.begin()
        batch.draw(background, 0f, 0f,WORLD_W,99f)
        batch.end()
        drawSpellsZone(currentFocus)
        drawDescriptionZone()
    }

    private fun drawSpellsZone(currentFocus: FocusTarget) {
        batch.begin()
        val nbSpells = spellBarState.spells.size

        if (nbSpells > 0) {
            val originalFontScaleX = font.data.scaleX
            val originalFontScaleY = font.data.scaleY

            val totalCardsWidth = cardWidth * nbSpells
            val spaceBetweenCards = (SPELLS_W - totalCardsWidth) / (nbSpells + 1)

            spellBarState.spells.forEachIndexed { i, spell ->
                val cardX = SPELLS_X + i * (spaceBetweenCards + cardWidth) + spaceBetweenCards
                val cardY = SPELLS_Y + (SPELLS_H - cardHeight) / 2 // Center vertically within SPELLS_H

                if (currentFocus is FocusTarget.Spell && currentFocus.index == i) {
                    batch.end() // On suspend le batch pour utiliser le shapeRenderer

                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
                    // On utilise une couleur Or moins agressive que Orange
                    shapeRenderer.color = Color(1f, 0.84f, 0f, 1f)

                    // On dessine un rectangle aux bords arrondis (si possible) ou simple
                    Gdx.gl.glLineWidth(2f) // Optionnel : épaissir un peu
                    shapeRenderer.rect(cardX - 1f, cardY - 1f, cardWidth + 2f, cardHeight + 2f)
                    shapeRenderer.end()

                    batch.begin() // On reprend le batch
                }

                // Draw paper background for the card
                batch.draw(paperBackground, cardX, cardY, cardWidth, cardHeight)

                font.color = Color.BLACK

                // 1. Draw main spell book icon (top-left)
                val mainBookTexture = getSpellBookTexture(spell.cost)
                val mainBookIconX = cardX + cardWidth - cardPadding - mainBookIconSize / 2
                val mainBookIconY = cardY + cardHeight - cardPadding - mainBookIconSize / 2
                batch.draw(mainBookTexture, mainBookIconX, mainBookIconY, mainBookIconSize, mainBookIconSize)

                // 2. Draw spell name (title) below main icon, centered, smaller font, wrap
                font.data.setScale(titleFontScale)
                glyphLayout.setText(font, spell.name, Color.BLACK, cardWidth - 2 * cardPadding, Align.center, true)
                val titleTextY = mainBookIconY - cardPadding // Position below main icon
                font.draw(batch, glyphLayout, cardX + cardPadding, titleTextY) // font.draw uses top-left for Y

                // 3. Draw cost icons and values (bottom-middle)
                val costElements = mutableListOf<Pair<Texture, Int>>()
                if (spell.cost.fireCost > 0) costElements.add(fireSymbol to spell.cost.fireCost)
                if (spell.cost.waterCost > 0) costElements.add(waterSymbol to spell.cost.waterCost)
                if (spell.cost.earthCost > 0) costElements.add(earthSymbol to spell.cost.earthCost)
                if (spell.cost.windCost > 0) costElements.add(windSymbol to spell.cost.windCost)

                val totalCostIconsAndValuesWidth =
                    costElements.size * costIconSize + (costElements.size - 1) * cardPadding
                var currentCostElementX = cardX + (cardWidth - totalCostIconsAndValuesWidth) / 2

                // Position cost elements at the bottom of the card
                val costIconsY = cardY + cardVerticalContentPadding + costValueLineHeight // Icons above their values, with padding from bottom
                font.data.setScale(costValueFontScale)
                costElements.forEach { (texture, costValue) ->
                    batch.draw(texture, currentCostElementX, costIconsY, costIconSize, costIconSize)
                    glyphLayout.setText(font, costValue.toString(), Color.BLACK, costIconSize, Align.center, false)
                    font.draw(
                        batch,
                        glyphLayout,
                        currentCostElementX,
                        costIconsY - costValueLineHeight + glyphLayout.height
                    ) // Draw cost value below icon

                    currentCostElementX += costIconSize + cardPadding
                }
            }
            // Reset font scale to original after drawing all cards
            font.data.setScale(originalFontScaleX, originalFontScaleY)
        }
        batch.end()
    }

    private fun getSpellBookTexture(spellCost: SpellCost): Texture {
        return when {
            spellCost.isLightningSpell() -> lightningBook
            spellCost.isMagmaSpell() -> magmaBook
            spellCost.isIceSpell() -> iceBook
            spellCost.isFairySpell() -> fairyBook
            spellCost.isLightSpell() -> lightBook
            spellCost.isDarkSpell() -> darkBook
            spellCost.isFireSpell() -> fireBook
            spellCost.isWaterSpell() -> waterBook
            spellCost.isEarthSpell() -> earthBook
            spellCost.isWindSpell() -> windBook
            else -> fireBook // Default to fire book if no specific type or combination is found
        }
    }

    private fun drawDescriptionZone() {
        batch.begin()
        // Dessin du fond
        batch.draw(paperBackground, SPELLS_DESCRIPTION_X, SPELLS_Y, SPELLS_DESCRIPTION_W, SPELLS_H)

        val paddingX = 15f
        val titleY = SPELLS_Y + SPELLS_H - 12f
        val descriptionTopY = titleY - 22f // Y de départ du texte (le haut du bloc)

        // --- TITRE ---
        font.color = Color.BLACK
        font.data.setScale(0.55f)
        font.draw(batch, spellBarState.title, SPELLS_DESCRIPTION_X + paddingX, titleY)

        // --- DESCRIPTION ---
        font.data.setScale(0.38f)
        val lineH = font.lineHeight // Hauteur d'une ligne de texte
        val iconSize = 8f
        val textIndent = 38f // Espace à gauche pour les icônes
        val iconX = SPELLS_DESCRIPTION_X + paddingX

        if (spellBarState.title == "Table des Gains") {
            val verticalOffset = (lineH + iconSize) / 2f

            // Ligne 1 (Index 0) : Les 4 runes côte à côte
            val firstLineIconY = descriptionTopY - verticalOffset
            val runes = listOf(fireSymbol, waterSymbol, earthSymbol, windSymbol)
            val smallRuneSize = 8f
            runes.forEachIndexed { index, texture ->
                batch.draw(texture, iconX + (index * 9f), firstLineIconY + 1f, smallRuneSize, smallRuneSize)
            }

            // Lignes suivantes (Index 1 à 5) : Loot unique
            val otherIcons = listOf(healIcon, simpleCoinIcon, multiCoinIcon, bagIcon, jokerIcon)
            otherIcons.forEachIndexed { index, texture ->
                val lineIndex = index + 1
                // On calcule le Y du haut de la ligne correspondante
                val lineTopY = descriptionTopY - (lineIndex * lineH) - 6f
                val iconY = lineTopY + (lineH - iconSize) / 2f

                batch.draw(texture, iconX + 28f, iconY, iconSize, iconSize)
            }

            // Dessin du texte décalé
            glyphLayout.setText(font, spellBarState.description, Color.BLACK,
                SPELLS_DESCRIPTION_W - paddingX - textIndent, Align.left, true)
            // Le texte se dessine à partir de descriptionTopY (le haut)
            font.draw(batch, glyphLayout, SPELLS_DESCRIPTION_X + paddingX + textIndent, descriptionTopY)

        } else {
            // Mode normal pour les sorts
            glyphLayout.setText(font, spellBarState.description, Color.BLACK,
                SPELLS_DESCRIPTION_W - (paddingX * 2), Align.left, true)
            font.draw(batch, glyphLayout, SPELLS_DESCRIPTION_X + paddingX, descriptionTopY)
        }

        font.data.setScale(1f)
        batch.end()
    }

    fun dispose() {
        paperBackground.disposeSafely()
        fireBook.disposeSafely()
        waterBook.disposeSafely()
        earthBook.disposeSafely()
        windBook.disposeSafely()
        lightningBook.disposeSafely()
        magmaBook.disposeSafely()
        iceBook.disposeSafely()
        fairyBook.disposeSafely()
        lightBook.disposeSafely()
        darkBook.disposeSafely()
        fireSymbol.disposeSafely()
        waterSymbol.disposeSafely()
        earthSymbol.disposeSafely()
        windSymbol.disposeSafely()
        healIcon.disposeSafely()
        simpleCoinIcon.disposeSafely()
        multiCoinIcon.disposeSafely()
        bagIcon.disposeSafely()
        jokerIcon.disposeSafely()
    }
}

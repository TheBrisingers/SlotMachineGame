package fr.thebrisingers.slotmachinegame.data

import fr.thebrisingers.slotmachinegame.data.machine.Earning
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import kotlin.math.round

const val SLOT_PRICE = 5
const val INITIAL_COINS = 30
const val HERO_MAX_HEALTH = 50

val FIRE_SYMBOL_EARNING = Earning(1, 3)
val WATER_SYMBOL_EARNING = Earning(1, 3)
val EARTH_SYMBOL_EARNING = Earning(1, 3)
val WIND_SYMBOL_EARNING = Earning(1, 3)

val SIMPLE_COIN_SYMBOL_EARNING = Earning((1.5 * SLOT_PRICE).toInt(), 3 * SLOT_PRICE)
val MULTIPLE_COIN_SYMBOL_EARNING = Earning(3 * SLOT_PRICE, 5 * SLOT_PRICE)
val COIN_BAG_SYMBOL_EARNING = Earning(5 * SLOT_PRICE, 10 * SLOT_PRICE)

val JOKER_SYMBOL_EARNING = Earning(0, 100 * SLOT_PRICE)

val HEAL_SYMBOL_EARNING = Earning(round(0.1 * HERO_MAX_HEALTH).toInt(), round(0.25 * HERO_MAX_HEALTH).toInt())

val WHEEL_SYMBOL_PROPORTION = mapOf(
    Symbol.FIRE to 7,
    Symbol.WATER to 7,
    Symbol.EARTH to 7,
    Symbol.WIND to 7,
    Symbol.SIMPLE_COIN to 7,
    Symbol.MULTIPLE_COIN to 4,
    Symbol.COIN_BAG to 2,
    Symbol.JOKER to 1,
    Symbol.HEAL to 2
)

fun Map<Symbol, Int>.toRandomWheelValue(): List<Symbol> = this.flatMap { (symbol, nbSymbol) -> List(nbSymbol) { symbol } }.shuffled()


// Monde total
const val WORLD_W = 1280f
const val WORLD_H = 720f          // format 16:9 classique

// Zone de combat — moitié gauche
const val COMBAT_X = 0f
const val COMBAT_W = 640f         // exactement la moitié
const val SPELLS_H = 150f         // hauteur de la bande sorts
const val COMBAT_Y = SPELLS_H
const val COMBAT_H = WORLD_H - SPELLS_H   // 570f

// Panneau droit
const val PANEL_X  = COMBAT_W     // 640f
const val PANEL_Y  = SPELLS_H     // 150f
const val PANEL_W  = WORLD_W - COMBAT_W   // 640f
const val PANEL_H  = WORLD_H - SPELLS_H  // 570f

// Bande sorts
const val SPELLS_X = 0f
const val SPELLS_Y = 0f
const val SPELLS_W = WORLD_W      // 1280f — pleine largeur

// Mage
const val MAGE_W    = 60f
const val MAGE_H    = 80f
const val MAGE_X    = COMBAT_X + COMBAT_W - 120f  // collé à droite de la zone combat
const val MAGE_Y    = COMBAT_Y + 60f

// Ennemis — espacés dans la zone combat
const val ENEMY_W   = 55f
const val ENEMY_H   = 70f
const val ENEMY_START_X = COMBAT_X + 60f   // premier ennemi
const val ENEMY_GAP = 120f                  // espace entre chaque ennemi

// Boutons de sorts
const val SPELL_BTN_H   = 110f             // hauteur bouton dans la bande de 150
const val SPELL_BTN_Y   = 20f             // marge depuis le bas
const val SPELL_BTN_GAP = 20f             // espace entre boutons

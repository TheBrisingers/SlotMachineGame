package fr.thebrisingers.slotmachinegame.data

import fr.thebrisingers.slotmachinegame.data.machine.Earning
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import kotlin.math.round

const val SPIN_PRICE = 5
const val INITIAL_COINS = 50
const val HERO_MAX_HEALTH = 50

const val MAX_WAVES = 5

val FIRE_SYMBOL_EARNING = Earning(1, 3)
val WATER_SYMBOL_EARNING = Earning(1, 3)
val EARTH_SYMBOL_EARNING = Earning(1, 3)
val WIND_SYMBOL_EARNING = Earning(1, 3)

val SIMPLE_COIN_SYMBOL_EARNING = Earning((1.5 * SPIN_PRICE).toInt(), 3 * SPIN_PRICE)
val MULTIPLE_COIN_SYMBOL_EARNING = Earning(3 * SPIN_PRICE, 5 * SPIN_PRICE)
val COIN_BAG_SYMBOL_EARNING = Earning(5 * SPIN_PRICE, 10 * SPIN_PRICE)

val JOKER_SYMBOL_EARNING = Earning(0, 100 * SPIN_PRICE)

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

fun Map<Symbol, Int>.toRandomWheelValue(): List<Symbol> =
    this.flatMap { (symbol, nbSymbol) -> List(nbSymbol) { symbol } }.shuffled()

// écran roulette 240 160
// combat 240 160
// spellbar 480 110

// Monde total
const val WORLD_W = 480f
const val WORLD_H = 270f          // format 16:9 classique

// Zone de combat — moitié gauche
const val COMBAT_X = 0f
const val COMBAT_W = WORLD_W / 2         // exactement la moitié
const val SPELLS_H = 90f         // hauteur de la bande sorts
const val COMBAT_Y = SPELLS_H
const val COMBAT_H = WORLD_H - SPELLS_H

// Panneau droit
const val PANEL_X = COMBAT_W     // 640f
const val PANEL_Y = SPELLS_H     // 150f
const val PANEL_W = WORLD_W - COMBAT_W   // 640f
const val PANEL_H = WORLD_H - SPELLS_H  // 570f

// Inventory
const val INVENTORY_W = 64f
const val INVENTORY_H = COMBAT_H
const val INVENTORY_X = PANEL_X - INVENTORY_W / 2
const val INVENTORY_Y = COMBAT_Y


// Bande sorts
const val SPELLS_X = 0f
const val SPELLS_Y = 0f
const val SPELLS_W = 2 * WORLD_W / 3      // 1280f — pleine largeur
const val SPELLS_DESCRIPTION_X = SPELLS_W
const val SPELLS_DESCRIPTION_W = WORLD_W - SPELLS_W


const val FLOOR_Y = 32f
const val PADDING_BATTLES = 12f

// Mage
const val MAGE_W = 32f
const val MAGE_H = 32f
const val MAGE_X = COMBAT_X + COMBAT_W - PADDING_BATTLES - MAGE_W
const val MAGE_Y = COMBAT_Y + FLOOR_Y

// Ennemis — espacés dans la zone combat
const val ENEMY_W = 32f
const val ENEMY_H = 32f
const val ENEMY_START_X = COMBAT_X + PADDING_BATTLES   // premier ennemi
const val ENEMY_Y = COMBAT_Y + FLOOR_Y

val ENEMY_Y_OFFSET = listOf(0f, ENEMY_H / 3, 2 * ENEMY_H / 3)
const val ENEMY_GAP = 12f                  // espace entre chaque ennemi

// Boutons de sorts
const val SPELL_BTN_H = 110f             // hauteur bouton dans la bande de 150
const val SPELL_BTN_Y = 20f             // marge depuis le bas
const val SPELL_BTN_GAP = 20f             // espace entre boutons

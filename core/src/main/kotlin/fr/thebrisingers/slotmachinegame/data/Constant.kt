package fr.thebrisingers.slotmachinegame.data

import fr.thebrisingers.slotmachinegame.data.machine.Earning
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import kotlin.math.round

const val SLOT_PRICE = 5
const val HERO_HEALTH = 50

val FIRE_SYMBOL_EARNING = Earning(1, 3)
val WATER_SYMBOL_EARNING = Earning(1, 3)
val EARTH_SYMBOL_EARNING = Earning(1, 3)
val WIND_SYMBOL_EARNING = Earning(1, 3)

val SIMPLE_COIN_SYMBOL_EARNING = Earning((1.5 * SLOT_PRICE).toInt(), 3 * SLOT_PRICE)
val MULTIPLE_COIN_SYMBOL_EARNING = Earning(3 * SLOT_PRICE, 5 * SLOT_PRICE)
val COIN_BAG_SYMBOL_EARNING = Earning(5 * SLOT_PRICE, 10 * SLOT_PRICE)

val JOKER_SYMBOL_EARNING = Earning(0, 100 * SLOT_PRICE)

val HEAL_SYMBOL_EARNING = Earning(round(0.1 * HERO_HEALTH).toInt(), round(0.25 * HERO_HEALTH).toInt())

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

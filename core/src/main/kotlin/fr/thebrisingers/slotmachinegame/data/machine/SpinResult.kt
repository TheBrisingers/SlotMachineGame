package fr.thebrisingers.slotmachinegame.data.machine

import fr.thebrisingers.slotmachinegame.data.*
import fr.thebrisingers.slotmachinegame.data.spell.Symbol


data class SpinResult(
    val firstWheel: List<Symbol>,
    val secondWheel: List<Symbol>,
    val thirdWheel: List<Symbol>,
) {

    fun getFirstRowIncome() = calculateIncome(firstWheel[0], secondWheel[0], thirdWheel[0])
    fun getSecondRowIncome() = calculateIncome(firstWheel[1], secondWheel[1], thirdWheel[1])
    fun getThirdRowIncome() = calculateIncome(firstWheel[2], secondWheel[2], thirdWheel[2])
    fun getFirstDiagonalIncome() = calculateIncome(firstWheel[0], secondWheel[1], thirdWheel[2])
    fun getSecondDiagonalIncome() = calculateIncome(firstWheel[2], secondWheel[1], thirdWheel[0])

    private fun calculateIncome(first: Symbol, second: Symbol, third: Symbol): Pair<Symbol, Int>? {
        val resolved = listOf(first, second, third).firstOrNull { it != Symbol.JOKER }
            ?: return Symbol.JOKER to 3

        fun Symbol.matches() = this == Symbol.JOKER || this == resolved

        return when {
            !first.matches() -> null
            !second.matches() -> null
            !third.matches() -> resolved to mapEarning[resolved]!!.twoSymbolWin
            else -> resolved to mapEarning[resolved]!!.threeSymbolWin
        }
    }

    companion object {
        private val mapEarning = mapOf(
            Symbol.FIRE to FIRE_SYMBOL_EARNING,
            Symbol.WATER to WATER_SYMBOL_EARNING,
            Symbol.EARTH to EARTH_SYMBOL_EARNING,
            Symbol.WIND to WIND_SYMBOL_EARNING,
            Symbol.SIMPLE_COIN to SIMPLE_COIN_SYMBOL_EARNING,
            Symbol.MULTIPLE_COIN to MULTIPLE_COIN_SYMBOL_EARNING,
            Symbol.COIN_BAG to COIN_BAG_SYMBOL_EARNING,
            Symbol.JOKER to JOKER_SYMBOL_EARNING,
            Symbol.HEAL to HEAL_SYMBOL_EARNING,
        )
    }
}


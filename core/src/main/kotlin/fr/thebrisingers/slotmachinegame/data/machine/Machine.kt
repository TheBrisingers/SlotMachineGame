package fr.thebrisingers.slotmachinegame.data.machine

import fr.thebrisingers.slotmachinegame.data.INITIAL_COINS
import fr.thebrisingers.slotmachinegame.data.SPIN_PRICE
import fr.thebrisingers.slotmachinegame.data.spell.Symbol

class Machine(
    var wheelOne: List<Symbol>,
    var wheelTwo: List<Symbol>,
    var wheelThree: List<Symbol>,
    initialCoins: Int = INITIAL_COINS,
){
    var currentCoins = initialCoins
        private set
    var spinResult: SpinResult = SpinResult(
        wheelOne,
        wheelTwo,
        wheelThree
    )

    data class SpinIncomes(
        val runes: List<Pair<Symbol, Int>>,
        val coins: Int,
        val heals: Int
    )

    fun getIncomes(): SpinIncomes {
        val incomes = listOf(
            spinResult.getFirstRowIncome(),
            spinResult.getSecondRowIncome(),
            spinResult.getThirdRowIncome(),
            spinResult.getFirstDiagonalIncome(),
            spinResult.getSecondDiagonalIncome()
        ).filterNotNull()

        val runes = incomes.filter { it.first in listOf(Symbol.EARTH, Symbol.WIND, Symbol.WATER, Symbol.FIRE) }

        val coins = incomes
            .filter { it.first in listOf(Symbol.SIMPLE_COIN, Symbol.MULTIPLE_COIN, Symbol.COIN_BAG) }
            .sumOf { it.second }

        val heals = incomes
            .filter { it.first in listOf(Symbol.HEAL) }
            .sumOf { it.second }

        return SpinIncomes(runes, coins, heals)


    }

    fun updateSpinResult(wheelOne: List<Symbol>, wheelTwo: List<Symbol>, wheelThree: List<Symbol>){
        spinResult = SpinResult(
            wheelOne.subList(0, 3),
            wheelTwo.subList(0, 3),
            wheelThree.subList(0, 3)
        )
    }

    fun shuffleWheels(){
        wheelOne = wheelOne.shuffled()
        wheelTwo = wheelTwo.shuffled()
        wheelThree = wheelThree.shuffled()
        updateSpinResult(wheelOne, wheelTwo, wheelThree)
    }
}

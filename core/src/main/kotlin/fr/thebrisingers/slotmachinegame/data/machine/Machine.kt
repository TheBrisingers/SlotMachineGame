package fr.thebrisingers.slotmachinegame.data.machine

import fr.thebrisingers.slotmachinegame.data.INITIAL_COINS
import fr.thebrisingers.slotmachinegame.data.SLOT_PRICE
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

    fun updateCoins(coinModifier: Int): Boolean {
        if(currentCoins + coinModifier > SLOT_PRICE) {
            currentCoins += coinModifier
            return true
        } else {
            return false
        }
    }

    fun getIncomes(): List<Pair<Symbol, Int>> {
        val incomes = listOf(
            spinResult.getFirstRowIncome(),
            spinResult.getSecondRowIncome(),
            spinResult.getThirdRowIncome(),
            spinResult.getFirstDiagonalIncome(),
            spinResult.getSecondDiagonalIncome()
        )
        return incomes.filterNotNull()
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

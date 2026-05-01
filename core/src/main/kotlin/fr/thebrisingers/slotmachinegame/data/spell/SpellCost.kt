package fr.thebrisingers.slotmachinegame.data.spell

data class SpellCost(
    val fireCost: Int = 0,
    val waterCost: Int = 0,
    val windCost: Int = 0,
    val earthCost: Int = 0,
) {
    val spellCostMap = mapOf(
        Symbol.FIRE to fireCost,
        Symbol.WATER to waterCost,
        Symbol.WIND to windCost,
        Symbol.EARTH to earthCost
    )
}

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

    private val totalElements = (if (fireCost > 0) 1 else 0) +
                                (if (waterCost > 0) 1 else 0) +
                                (if (windCost > 0) 1 else 0) +
                                (if (earthCost > 0) 1 else 0)

    fun isSingleElementSpell() = totalElements == 1
    fun isDualElementSpell() = totalElements == 2

    fun isFireSpell() = fireCost > 0 && isSingleElementSpell()
    fun isWaterSpell() = waterCost > 0 && isSingleElementSpell()
    fun isWindSpell() = windCost > 0 && isSingleElementSpell()
    fun isEarthSpell() = earthCost > 0 && isSingleElementSpell()

    // Dual element spells
    fun isLightningSpell() = fireCost > 0 && windCost > 0 && isDualElementSpell() // Fire + Air
    fun isMagmaSpell() = fireCost > 0 && earthCost > 0 && isDualElementSpell()   // Fire + Earth
    fun isIceSpell() = waterCost > 0 && windCost > 0 && isDualElementSpell()     // Water + Air
    fun isFairySpell() = earthCost > 0 && waterCost > 0 && isDualElementSpell()   // Earth + Water
    fun isLightSpell() = windCost > 0 && earthCost > 0 && isDualElementSpell()   // Air + Earth
    fun isDarkSpell() = waterCost > 0 && fireCost > 0 && isDualElementSpell()     // Water + Fire

    fun hasCost(): Boolean {
        return fireCost > 0 || waterCost > 0 || windCost > 0 || earthCost > 0
    }
}

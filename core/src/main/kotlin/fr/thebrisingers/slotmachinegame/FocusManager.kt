package fr.thebrisingers.slotmachinegame

import fr.thebrisingers.slotmachinegame.data.spell.Spell

class FocusManager(
    val spells: () -> List<Spell>,
) {
    // Les cibles sont recalculées à chaque fois
    private val targets
        get() = buildList {
            add(FocusTarget.Spin)
            spells().forEachIndexed { i, _ -> add(FocusTarget.Spell(i)) }
        }

    private var currentIndex = 0
    val current: FocusTarget
        get() = targets[currentIndex.coerceAtMost(targets.size - 1)]

    fun next() {
        currentIndex = (currentIndex + 1) % targets.size
    }

    fun isFocused(target: FocusTarget) = current == target
}

sealed class FocusTarget {
    data class Spell(val index: Int) : FocusTarget()
    object Spin : FocusTarget()
}

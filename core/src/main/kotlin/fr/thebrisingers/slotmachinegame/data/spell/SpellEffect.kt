package fr.thebrisingers.slotmachinegame.data.spell

data class SpellEffect(
    val target: Target,
    val value: Int,
    val additionalEffect: String? = null,
) {
    override fun toString(): String {
        return when(target){
            Target.FRONT -> "Premier monstre : $value PV"
            Target.BACK -> "Dernier monstre : $value PV"
            Target.ALL -> "Tous les monstres : $value PV"
            Target.SELF -> "Vous : $value PV"
        }
    }
}

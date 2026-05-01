package fr.thebrisingers.slotmachinegame.data.gameStatus

import fr.thebrisingers.slotmachinegame.data.caracter.Entity
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.SpellEffect

sealed class BattleEvent {
    data class SpellHit(val spell: Spell, val targets: List<Entity>) : BattleEvent()
    data class EnemyAttack(val enemyId: Int, val damage: Int) : BattleEvent()
}

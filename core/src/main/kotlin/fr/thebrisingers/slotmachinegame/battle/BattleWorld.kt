package fr.thebrisingers.slotmachinegame.battle

import fr.thebrisingers.slotmachinegame.data.caracter.Category
import fr.thebrisingers.slotmachinegame.data.caracter.Entity
import fr.thebrisingers.slotmachinegame.data.caracter.Faction
import fr.thebrisingers.slotmachinegame.data.caracter.Hero
import fr.thebrisingers.slotmachinegame.data.caracter.Monster
import fr.thebrisingers.slotmachinegame.data.gameStatus.BattleEvent
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.Target

class BattleWorld {

    val hero = Hero()

    val monsters = listOf(
        Monster(0, Category.ASSASSIN, Faction.SKELETON),
        Monster(1, Category.ARCHER, Faction.ZOMBIE),
        Monster(2, Category.SOLDIER, Faction.GOBLIN),
    ).sortedBy { it.id }

    var phase = TurnPhase.PLAYER_TURN
        private set

    var lastEvents = listOf<BattleEvent>()  // pour le log / animations
        private set

    fun castSpell(spell: Spell): Boolean {
        if (phase != TurnPhase.PLAYER_TURN) return false
        spell.spellEffect.forEach { effect ->
            val aliveMonsters = monsters.filter { it.isAlive }
            val target: List<Entity> = when (effect.target) {
                Target.FRONT -> monsters.subList(0, 1)
                Target.BACK -> monsters.subList(monsters.size - 1, monsters.size)
                Target.ALL -> monsters
                Target.SELF -> listOf(hero)
            }

            target.onEach {
                it.takeDamage(effect.value)
            }
        }
        phase = TurnPhase.RESOLUTION
        return true
    }

    /**
     * Appeler quand coup de machine
     */
    fun advanceMonsterTurn() {
        if (phase != TurnPhase.PLAYER_TURN) return
        phase = TurnPhase.MONSTER_TURN
        monsters.filter { it.isAlive }.onEach {
            it.advanceTurn()
            if (it.attackThisTurn) {
                hero.takeDamage(it.attackDamage)
            }
        }
        checkBattleStatus()
    }

    fun checkBattleStatus() {
        phase = when {
            !hero.isAlive -> TurnPhase.GAME_OVER
            monsters.none { it.isAlive } -> TurnPhase.VICTORY
            else -> TurnPhase.PLAYER_TURN
        }
    }
}

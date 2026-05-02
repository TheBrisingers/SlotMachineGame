package fr.thebrisingers.slotmachinegame.battle

import fr.thebrisingers.slotmachinegame.data.caracter.*
import fr.thebrisingers.slotmachinegame.data.gameStatus.BattleEvent
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.Target

class BattleState {

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

        phase = TurnPhase.RESOLUTION
        spell.spellEffect.forEach { effect ->
            val aliveMonsters = monsters.filter { it.isAlive }
            if (aliveMonsters.isNotEmpty()) {
                val target: List<Entity> = when (effect.target) {
                    Target.FRONT -> aliveMonsters.subList(0, 1)
                    Target.BACK -> aliveMonsters.subList(aliveMonsters.size - 1, aliveMonsters.size)
                    Target.ALL -> aliveMonsters
                    Target.SELF -> listOf(hero)
                }

                target.onEach {
                    it.takeDamage(effect.value)
                }
            }
        }
        checkBattleStatus()
        return true
    }

    /**
     * Appeler quand coup de machine
     */
    fun advanceMonsterTurn() {
        if (phase != TurnPhase.PLAYER_TURN) return
        phase = TurnPhase.MONSTER_TURN
        monsters.filter { it.isAlive }.onEach { monster ->
            val isAttacking = monster.advanceTurn()

            if (isAttacking) {
                // 2. On inflige les dégâts au héros
                hero.takeDamage(monster.attackDamage)

                // 3. On pourra appeler le reset APRES l'animation dans le Renderer
                // Ou on le fait ici si on n'attend pas l'animation :
                monster.resetTurnsUntilNextAttack()
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

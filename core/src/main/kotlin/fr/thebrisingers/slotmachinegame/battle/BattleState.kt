package fr.thebrisingers.slotmachinegame.battle

import fr.thebrisingers.slotmachinegame.data.MAX_WAVES
import fr.thebrisingers.slotmachinegame.data.caracter.*
import fr.thebrisingers.slotmachinegame.data.gameStatus.BattleEvent
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.Target

class BattleState {
    var waveNumber = 1
    private val difficultyScale = 0.2f
    val hero = Hero()

    var monsters: List<Monster> = spawnWave()

    var phase = TurnPhase.PLAYER_TURN
        private set

    var lastEvents = listOf<BattleEvent>()  // pour le log / animations
        private set

    private fun spawnWave(): List<Monster> {
        val multiplier = 1f + (waveNumber - 1) * difficultyScale

        // On génère 3 nouveaux monstres aléatoires
        return List(3) { id ->
            // On peut piocher aléatoirement dans une liste de catégories/factions
            val randomCategory = Category.values().random()
            val randomFaction = Faction.values().random()

            Monster(id, randomCategory, randomFaction, multiplier)
        }
    }

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
            monsters.none { it.isAlive } -> {
                if (waveNumber >= MAX_WAVES) {
                    TurnPhase.VICTORY // Victoire finale après 5 vagues
                } else {
                    startNextWave()
                    TurnPhase.PLAYER_TURN
                }
            }
            else -> TurnPhase.PLAYER_TURN
        }
    }

    private fun startNextWave() {
        waveNumber++
        monsters = spawnWave()

        hero.heal(20)

        phase = TurnPhase.PLAYER_TURN
        println("Vague $waveNumber commencée ! Multiplicateur : ${1f + (waveNumber - 1) * difficultyScale}")
    }
}

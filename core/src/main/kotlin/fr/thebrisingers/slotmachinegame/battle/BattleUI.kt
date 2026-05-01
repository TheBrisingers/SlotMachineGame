package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import ktx.actors.onClick

class BattleUI(
    val stage : Stage,
    private val spells: List<Spell>,
    private val onCast: (Spell) -> Unit,
    private val onSpinWheel: () -> Unit,
    private val world: BattleState
) {
    // ScreenViewport car la UI est en pixels écran, pas en unités monde
    init {
        val btnW = stage.width / (spells.size + 1)  // +1 pour le bouton skip
        val btnH = 80f
        val btnY = 20f  // 20px depuis le bas de l'écran

       spells.forEachIndexed { i, spell ->
            val btn = TextButton(spell.name, createSkin())
            btn.setBounds(i * btnW + 10f, btnY, btnW - 20f, btnH)
            btn.onClick { onCast(spell) }
            stage.addActor(btn)
        }

         val skipBtn = TextButton("Passer", createSkin())
        skipBtn.setBounds(spells.size * btnW + 10f, btnY, btnW - 20f, btnH)
        skipBtn.onClick { onSpinWheel() }
        stage.addActor(skipBtn)
    }
}

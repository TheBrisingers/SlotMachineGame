package fr.thebrisingers.slotmachinegame.battle.init

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.assets.disposeSafely

object InitRogue {
    private val RogueIdleSheet = Texture(Gdx.files.internal("monsters/Rogue/Rogue_idle.png"))
    private val RogueDeathSheet = Texture(Gdx.files.internal("monsters/Rogue/Rogue_death.png"))
    private val RogueGetItSheet = Texture(Gdx.files.internal("monsters/Rogue/Rogue_get_hit.png"))
    private val RogueAttackSheet = Texture(Gdx.files.internal("monsters/Rogue/Rogue_attack.png"))

    // IDLE
    val idleAnimation: Animation<TextureRegion> = run {
        val idleFrames = TextureRegion.split(RogueIdleSheet, 24, 24)[0]
        Animation(0.1f, *idleFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }
    val attackAnimation: Animation<TextureRegion> = run {
        val attackFrames = TextureRegion.split(RogueAttackSheet, 24, 24)[0]
        Animation(0.08f, *attackFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val getHitAnimation: Animation<TextureRegion> = run {
        val hitFrames = TextureRegion.split(RogueGetItSheet, 24, 24)[0]
        Animation(0.1f, *hitFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val deathAnimation: Animation<TextureRegion> = run {
        val deathFrames = TextureRegion.split(RogueDeathSheet, 24, 24)[0]
        Animation(0.15f, *deathFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }

    fun dispose() {
        RogueIdleSheet.disposeSafely()
        RogueDeathSheet.disposeSafely()
        RogueGetItSheet.disposeSafely()
        RogueDeathSheet.disposeSafely()
    }
}

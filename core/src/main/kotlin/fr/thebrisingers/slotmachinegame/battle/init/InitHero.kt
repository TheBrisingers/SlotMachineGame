package fr.thebrisingers.slotmachinegame.battle.init

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.assets.disposeSafely

object InitHero {
    private val playerIdleSheet = Texture(Gdx.files.internal("hero/Player_idle.png"))
    private val playerDeathSheet = Texture(Gdx.files.internal("hero/Player_death.png"))
    private val playerGetItSheet = Texture(Gdx.files.internal("hero/Player_get_hit.png"))
    private val playerAttackSheet = Texture(Gdx.files.internal("hero/Player_attack.png"))

    // IDLE
    val idleAnimation: Animation<TextureRegion> = run {
        val idleFrames = TextureRegion.split(playerIdleSheet, 24, 24)[0]
        idleFrames.forEach { it.flip(true, false) }
        Animation(0.1f, *idleFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }
    val attackAnimation: Animation<TextureRegion> = run {
        val attackFrames = TextureRegion.split(playerAttackSheet, 24, 24)[0]
        Animation(0.08f, *attackFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val getHitAnimation: Animation<TextureRegion> = run {
        val hitFrames = TextureRegion.split(playerGetItSheet, 24, 24)[0]
        Animation(0.1f, *hitFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val deathAnimation: Animation<TextureRegion> = run {
        val deathFrames = TextureRegion.split(playerDeathSheet, 24, 24)[0]
        Animation(0.15f, *deathFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }

    fun dispose() {
        playerIdleSheet.disposeSafely()
        playerDeathSheet.disposeSafely()
        playerGetItSheet.disposeSafely()
        playerDeathSheet.disposeSafely()
    }
}

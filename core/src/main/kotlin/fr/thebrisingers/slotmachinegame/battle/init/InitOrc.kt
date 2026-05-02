package fr.thebrisingers.slotmachinegame.battle.init

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.assets.disposeSafely

object InitOrc {
    private val OrcIdleSheet = Texture(Gdx.files.internal("monsters/Orc/Orc_idle.png"))
    private val OrcDeathSheet = Texture(Gdx.files.internal("monsters/Orc/Orc_death.png"))
    private val OrcGetItSheet = Texture(Gdx.files.internal("monsters/Orc/Orc_get_hit.png"))
    private val OrcAttackSheet = Texture(Gdx.files.internal("monsters/Orc/Orc_attack.png"))

    // IDLE
    val idleAnimation: Animation<TextureRegion> = run {
        val idleFrames = TextureRegion.split(OrcIdleSheet, 40, 40)[0]
        Animation(0.1f, *idleFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }
    val attackAnimation: Animation<TextureRegion> = run {
        val attackFrames = TextureRegion.split(OrcAttackSheet, 40, 40)[0]
        Animation(0.08f, *attackFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val getHitAnimation: Animation<TextureRegion> = run {
        val hitFrames = TextureRegion.split(OrcGetItSheet, 40, 40)[0]
        Animation(0.1f, *hitFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val deathAnimation: Animation<TextureRegion> = run {
        val deathFrames = TextureRegion.split(OrcDeathSheet, 40, 40)[0]
        Animation(0.15f, *deathFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }

    fun dispose() {
        OrcIdleSheet.disposeSafely()
        OrcDeathSheet.disposeSafely()
        OrcGetItSheet.disposeSafely()
        OrcDeathSheet.disposeSafely()
    }
}

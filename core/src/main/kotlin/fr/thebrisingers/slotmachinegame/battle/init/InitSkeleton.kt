package fr.thebrisingers.slotmachinegame.battle.init

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.assets.disposeSafely

object InitSkeleton {
    private val SkeletonIdleSheet = Texture(Gdx.files.internal("monsters/Skeleton/Skeleton_idle.png"))
    private val SkeletonDeathSheet = Texture(Gdx.files.internal("monsters/Skeleton/Skeleton_death.png"))
    private val SkeletonGetItSheet = Texture(Gdx.files.internal("monsters/Skeleton/Skeleton_get_hit.png"))
    private val SkeletonAttackSheet = Texture(Gdx.files.internal("monsters/Skeleton/Skeleton_attack.png"))

    // IDLE
    val idleAnimation: Animation<TextureRegion> = run {
        val idleFrames = TextureRegion.split(SkeletonIdleSheet, 16, 16)[0]
        Animation(0.1f, *idleFrames)
    }.apply {
        playMode = Animation.PlayMode.LOOP
    }
    val attackAnimation: Animation<TextureRegion> = run {
        val attackFrames = TextureRegion.split(SkeletonAttackSheet, 16, 16)[0]
        Animation(0.08f, *attackFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val getHitAnimation: Animation<TextureRegion> = run {
        val hitFrames = TextureRegion.split(SkeletonGetItSheet, 16, 16)[0]
        Animation(0.1f, *hitFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }
    val deathAnimation: Animation<TextureRegion> = run {
        val deathFrames = TextureRegion.split(SkeletonDeathSheet, 16, 16)[0]
        Animation(0.15f, *deathFrames)
    }.apply {
        playMode = Animation.PlayMode.NORMAL
    }

    fun dispose() {
        SkeletonIdleSheet.disposeSafely()
        SkeletonDeathSheet.disposeSafely()
        SkeletonGetItSheet.disposeSafely()
        SkeletonDeathSheet.disposeSafely()
    }
}

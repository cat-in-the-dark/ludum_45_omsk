package org.catinthedark.itsadeal.lib

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

object AnimationUtils {
    /**
     * Helper to create libgdx [Animation]
     * Example usage:
     * ```
     * AnimationUtils.loopingAnimation(Const.UI.animationSpeed, frames, Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3))
     * ```
     *
     * Example animation will consist of 4 frames.
     *
     * @param speed is animation speed in parrots.
     * @param frames is a matrix of images.
     * @param frameIndexes is an collection of indexes for images in the [frames] to be used in animation.
     */
    fun loopingAnimation(
        speed: Float,
        frames: Array<Array<TextureRegion>>,
        vararg frameIndexes: Pair<Int, Int>
    ): Animation<TextureRegion> {
        val array = com.badlogic.gdx.utils.Array<TextureRegion>()
        frameIndexes.forEach {
            array.add(frames[it.first][it.second])
        }
        return Animation(speed, array, Animation.PlayMode.LOOP)
    }
}

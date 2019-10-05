package org.catinthedark.itsadeal.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont

fun AssetManager.font(name: String) = this.at<BitmapFont>(name)
fun AssetManager.music(name: String) = this.at<Music>(name)
fun AssetManager.sound(name: String) = this.at<Sound>(name)
fun AssetManager.texture(name: String) = this.at<Texture>(name)

package org.catinthedark.jvcrplotter.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.DEFAULT_CHARS
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader

object Assets {
    private const val RUSSIAN_CHARACTERS = DEFAULT_CHARS +
        "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
        "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
        "$"


    fun load(): AssetManager {
        return AssetManager().apply {
            Names.textures.forEach { load(it, Texture::class.java) }

            val resolver = InternalFileHandleResolver()
            setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
            setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
            load(Names.FONT_BIG, BitmapFont::class.java, FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 32
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontFileName = "fonts/cyrfont.ttf"
            })
            load(Names.FONT_BIG_GREEN, BitmapFont::class.java, FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 32
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontParameters.color = Color(0x00d410ff)
                fontFileName = "fonts/cyrfont.ttf"
            })
            load(Names.FONT_SMALL, BitmapFont::class.java, FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 24
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontFileName = "fonts/cyrfont.ttf"
            })
            load(
                Names.FONT_SMALL_BLACK,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 20
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.BLACK
                    fontFileName = "fonts/cyrfont.ttf"
                })
            load(
                Names.FONT_SMALL_GRAY,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 20
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.GRAY
                    fontFileName = "fonts/cyrfont.ttf"
                })
            load(Names.FONT_BIG_SERIF, BitmapFont::class.java, FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                fontParameters.size = 54
                fontParameters.characters = RUSSIAN_CHARACTERS
                fontParameters.color = Color.BLACK
                fontFileName = "fonts/cyrillic_pixel.ttf"
            })
            load(
                Names.FONT_SMALL_WHITE,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 20
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.WHITE
                    fontFileName = "fonts/cyrfont.ttf"
                })
            load(
                Names.FONT_MEDIUM_BLACK,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 32
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.BLACK
                    fontFileName = "fonts/cyrfont.ttf"
                })
            load(
                Names.FONT_MEDIUM_WHITE,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 32
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.WHITE
                    fontFileName = "fonts/cyrfont.ttf"
                })
            load(Names.FONT_MEDIUM_MONOSPACE_BLACK,
                BitmapFont::class.java,
                FreetypeFontLoader.FreeTypeFontLoaderParameter().apply {
                    fontParameters.size = 24
                    fontParameters.characters = RUSSIAN_CHARACTERS
                    fontParameters.color = Color.BLACK
                    fontFileName = "fonts/SourceCodePro-Medium.ttf"
                })
        }
    }

    object Names {
        val FONT_BIG = "font_big.ttf"
        val FONT_BIG_GREEN = "font_big_green.ttf"
        val FONT_SMALL = "font_small.ttf"
        val FONT_SMALL_BLACK = "font_small_black.ttf"
        val FONT_SMALL_GRAY = "font_small_gray.ttf"
        val FONT_SMALL_WHITE = "font_small_white.ttf"
        val FONT_MEDIUM_BLACK = "font_medium_black.ttf"
        val FONT_MEDIUM_WHITE = "font_medium_white.ttf"
        val FONT_BIG_SERIF = "font_big_serif.ttf"
        val FONT_MEDIUM_MONOSPACE_BLACK = "font_medium_monospace_black.ttf"
        val TASK_1 = "tasks/task1.png"
        val TASK_2 = "tasks/task2.png"
        val TASK_3 = "tasks/task3.png"
        val TASK_4 = "tasks/task4.png"
        val TASK_5 = "tasks/task5.png"
        val TASK_6 = "tasks/task6.png"
        val TASK_7 = "tasks/task7.png"
        val TASK_8 = "tasks/task8.png"
        val LOGO = "textures/title.png"
        val TITLE = "textures/art.png"
        val MONIK = "textures/monik.png"
        val BUTTON = "textures/knopka.png"
        val BUTTON_RED = "textures/knopka_red.png"
        val ICON_MOVE = "textures/icon_move.png"
        val ICON_UNSET_PLOT = "textures/icon_unset_plot.png"
        val ICON_SET_PLOT = "textures/icon_set_plot.png"
        val BUTTON_INACTIVE = "textures/knopka_inactive.png"
        val CURSOR_FRAME = "textures/frame_9.png"
        val ERROR_FRAME = "textures/frame_red_9.png"
        val LINE_FRAME = "textures/frame_line_9.png"
        val HELP_FRAME = "textures/help_frame_9.png"
        val ARROW = "textures/arrow.png"
        val WORKSPACE = "textures/workspace.png"
        val EDITOR = "textures/editor.png"
        val TASK = "textures/task.png"
        val MAN = "textures/man.png"
        val PLOTTER = "textures/plotter.png"
        val TUTORIAL1 = "textures/tutorial1.png"
        val TUTORIAL2 = "textures/tutorial2.png"
        val TUTORIAL3 = "textures/tutorial3.png"
        val TUTORIAL3_5 = "textures/tutorial3_5.png"
        val TUTORIAL4 = "textures/tutorial4.png"
        val TUTORIAL5 = "textures/tutorial5.png"

        val textures = listOf(
            LOGO,
            TITLE,
            MONIK,
            BUTTON,
            BUTTON_RED,
            BUTTON_INACTIVE,
            CURSOR_FRAME,
            ERROR_FRAME,
            LINE_FRAME,
            HELP_FRAME,
            ARROW,
            WORKSPACE,
            ICON_MOVE,
            ICON_SET_PLOT,
            ICON_UNSET_PLOT,
            EDITOR,
            TASK,
            MAN,
            PLOTTER,
            TUTORIAL1,
            TUTORIAL2,
            TUTORIAL3,
            TUTORIAL3_5,
            TUTORIAL4,
            TUTORIAL5,
            TASK_1,
            TASK_2,
            TASK_3,
            TASK_4,
            TASK_5,
            TASK_6,
            TASK_7,
            TASK_8
        )
    }
}

inline fun <reified T> AssetManager.at(name: String): T {
    return get(name, T::class.java)
}

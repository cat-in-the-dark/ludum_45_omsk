package org.catinthedark.jvcrplotter.game.states

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Stage
import org.catinthedark.jvcrplotter.game.*
import org.catinthedark.jvcrplotter.game.asm.Interpreter
import org.catinthedark.jvcrplotter.game.asm.Operation
import org.catinthedark.jvcrplotter.game.asm.State
import org.catinthedark.jvcrplotter.game.editor.Editor
import org.catinthedark.jvcrplotter.game.interruptions.buildInterruptionsRegistry
import org.catinthedark.jvcrplotter.game.plotter.PlotState
import org.catinthedark.jvcrplotter.game.plotter.PlotVRAM
import org.catinthedark.jvcrplotter.game.plotter.createPlot
import org.catinthedark.jvcrplotter.game.ui.Button
import org.catinthedark.jvcrplotter.game.ui.CompositeNinePatchButton
import org.catinthedark.jvcrplotter.game.ui.EditorRender
import org.catinthedark.jvcrplotter.lib.*
import org.catinthedark.jvcrplotter.lib.states.IState
import org.slf4j.LoggerFactory

class PlottingScreenState : IState {
    private val logger = LoggerFactory.getLogger(javaClass)
    private var time: Float = 0f
    private lateinit var interpreter: Interpreter
    private var ok = true

    private val hud: Stage by lazy { IOC.atOrFail<Stage>("hud") }
    private var instructions: List<Operation> = listOf()
    private val am: AssetManager by lazy { IOC.atOrFail<AssetManager>("assetManager") }
    private lateinit var state: State
    private lateinit var plotState: PlotState
    private var running: Boolean = true
    private var runError: Boolean = false
    private var errorMessage: String? = ""
    private lateinit var editor: Editor
    private val cursorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.LINE_FRAME), 6, 6, 6, 6) }
    private val errorFrame: NinePatch by lazy { NinePatch(am.texture(Assets.Names.ERROR_FRAME), 6, 6, 6, 6) }
    private val font by lazy { am.font(Assets.Names.FONT_BIG_GREEN) }
    private val editorRender: EditorRender by lazy {
        EditorRender(
            hud.batch, font,
            cursorFrame,
            errorFrame
        )
    }
    private lateinit var after: ITimeBarrier
    private val backBtn = Button(1185, 580, 1255, 655, onClick = {
        val prevState = IOC.get("previousState")
        if (prevState != null) {
            IOC.put("state", prevState)
        }
    })
    private val popupBgTexture: Texture by lazy { createPopupBgTexture() }

    private fun createPopupBgTexture(): Texture {
        val w = 352 + 352 + 8 + 20 + 20
        val h = 430
        val pixmap = Pixmap(w, h, Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.CYAN)
        pixmap.fill()
        val tex = Texture(pixmap)
        pixmap.dispose()
        return tex
    }

    private val compileButton = CompositeNinePatchButton(
        910, 60, 220, 50, Assets.Names.BUTTON_RED,
        Rectangle(20f, 15f, 20f, 15f),
        "ПРЕРВАТЬ", {
            IOC.put("state", States.CODE_EDITOR_SCREEN)
        }
    )

    override fun onActivate() {
        logger.info("onActivate")
        runError = false
        errorMessage = null
        after = AfterBarrier(1f)
        instructions = IOC.atOrFail("instructions")
        editor = IOC.atOrFail("editor")
        logger.info("Got ${instructions.size} instructions")
        IOC.put("previousState", States.CODE_EDITOR_SCREEN)
        time = 0f
        running = true

        state = State(
            instructions = instructions
        )
        plotState = PlotState(
            PlotVRAM(width = Const.Plotter.WIDTH, height = Const.Plotter.HEIGHT),
            pencilColor = Const.Plotter.COLOR
        )
        interpreter = Interpreter(
            buildInterruptionsRegistry(
                state = state,
                plotState = plotState
            )
        )
    }

    private fun draw() {
        if (running) {
            try {
                running = interpreter.step(state)
                editor.setCursorPosition(0, state.programCounter)
                ok = true
            } catch (e: Exception) {
                running = false
                ok = false
                logger.error("FAIL", e)
                runError = true
                errorMessage = "ЕГГОГ\n" + e.message
            }
        } else {
            if (!ok) return

            val taskId: Int = IOC.atOrFail("currentTaskId")
            val task = Tasks.createTask(am, taskId)
            if (plotState.vram.check(task)) {
                after.invoke {
                    IOC.put(
                        "instructionsCount", IOC.atOrFail<Int>("instructionsCount") +
                            state.instructions.size
                    )
                    IOC.put("state", States.SUCCESS_SCREEN)
                }
            } else {
                after.invoke {
                    showErrorPopup(task)
                }
            }
        }
    }

    private fun showErrorPopup(task: List<List<Int>>) {
        val got = createPlot(plotState.vram)
        val expected = createPlot(task)

        hud.batch.managed {
            it.draw(popupBgTexture, 300f, 150f)
            font.draw(it, "ОБНАРУЖЕНО НЕСОВПАДЕНИЕ", 320f, 570f)
            it.draw(got, 320f, 170f, 352f, 352f)
            it.draw(expected, 320f + 352f + 8f, 170f, 352f, 352f)
        }
    }

    override fun onUpdate() {
        time += Gdx.graphics.deltaTime
        compileButton.update()
        backBtn.update()

        hud.batch.managed {
            it.draw(am.at<Texture>(Assets.Names.MONIK), 0f, 0f)
            it.draw(createPlot(plotState.vram), 700f, 310f, 352f, 352f)
            if (!runError) {
                am.font(Assets.Names.FONT_BIG_GREEN)
                    .draw(it, "Instructions: ${state.instructions.size}", 100f, 120f)
            }
        }

        renderEditorText(editor)
        compileButton.draw(hud.batch)

        draw()
    }

    private fun renderEditorText(editor: Editor) {
        editorRender.render(
            editor,
            runError,
            errorMessage,
            true
        )
    }

    override fun onExit() {

    }
}

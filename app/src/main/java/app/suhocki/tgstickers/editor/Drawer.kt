package app.suhocki.tgstickers.editor

import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.LifecycleCoroutineScope
import app.suhocki.tgstickers.editor.step.Step
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

class Drawer(
    lifecycleScope: LifecycleCoroutineScope,
    surfaceView: SurfaceView,
    private val steps: StateFlow<List<Step>>,
) {
    private var holder: SurfaceHolder? = null
        set(value) {
            field = value
            drawNextFrame()
        }

    init {
        lifecycleScope.launchWhenStarted {
            steps.collect {
                drawNextFrame()
            }
        }

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceCreated(holder: SurfaceHolder) {
                this@Drawer.holder = holder
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                this@Drawer.holder = holder
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                this@Drawer.holder = null
            }

            override fun surfaceRedrawNeeded(holder: SurfaceHolder) {
                this@Drawer.holder = holder
            }

        })
    }

    private fun drawNextFrame() {
        holder?.lockCanvas()?.let { canvas ->
            steps.value.forEach { step ->
                step.draw(canvas)
            }
            holder?.unlockCanvasAndPost(canvas)
        }
    }
}

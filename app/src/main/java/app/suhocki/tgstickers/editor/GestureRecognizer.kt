package app.suhocki.tgstickers.editor

import android.graphics.Point
import android.view.MotionEvent

class GestureRecognizer(
    private val move: (from: Point, to: Point) -> Unit
) {
    private var oldX = 0f
    private var oldY = 0f

    fun handle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> onActionDown(event)
            MotionEvent.ACTION_MOVE -> onActionMove(event)
            MotionEvent.ACTION_UP -> onActionUp(event)
        }
    }

    private fun onActionDown(event: MotionEvent) {
        oldX = event.x
        oldY = event.y
    }

    private fun onActionMove(event: MotionEvent) {
        val newX = event.x
        val newY = event.y

        fun getOffset(oldVal: Float, newVal: Float, current: Float): Float {
            return current + (newVal - oldVal)
        }

        val offsetX = getOffset(oldX, newX, 0f)
        val offsetY = getOffset(oldY, newY, 0f)

//        oldX = newX
//        oldY = newY
    }

    private fun onActionUp(event: MotionEvent) {
        move(
            Point(oldX.toInt(), oldY.toInt()),
            Point(event.x.toInt(), event.y.toInt())
        )
    }
}
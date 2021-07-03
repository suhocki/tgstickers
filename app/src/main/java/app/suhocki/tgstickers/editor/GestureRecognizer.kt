package app.suhocki.tgstickers.editor

import android.graphics.Point
import android.view.MotionEvent

class GestureRecognizer(
    private val start: (Point) -> Unit,
    private val move: (Point) -> Unit,
) {

    fun handle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> onActionDown(event)
            MotionEvent.ACTION_MOVE -> onActionMove(event)
            MotionEvent.ACTION_UP -> onActionUp(event)
        }
    }

    private fun onActionDown(event: MotionEvent) {
        start(event.getPoint())
    }

    private fun onActionMove(event: MotionEvent) {
        move(event.getPoint())
    }

    private fun onActionUp(event: MotionEvent) {
        move(event.getPoint())
    }

    companion object {
        private fun MotionEvent.getPoint(): Point {
            return Point(x.toInt(), y.toInt())
        }
    }
}
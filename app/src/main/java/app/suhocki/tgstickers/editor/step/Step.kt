package app.suhocki.tgstickers.editor.step

import android.content.ContentResolver
import android.graphics.*
import android.net.Uri
import androidx.core.graphics.contains

sealed class Step {
    abstract var center: Point

    abstract fun draw(canvas: Canvas)
    abstract fun contains(point: Point): Boolean
    abstract fun move(center: Point)

    class AddImage(
        var bitmap: Bitmap,
    ) : Step() {
        private var rect: Rect? = null

        override lateinit var center: Point

        override fun draw(canvas: Canvas) {
            if (!::center.isInitialized) {
                initializeCenter(canvas)
            }
            calculateRect(bitmap)

            canvas.drawBitmap(bitmap, null, requireNotNull(rect), null)
        }

        override fun contains(point: Point): Boolean {
            return rect?.contains(point) == true
        }

        override fun move(center: Point) {
            this.center = center
        }

        private fun initializeCenter(canvas: Canvas) {
            center = Point(
                canvas.clipBounds.centerX(),
                canvas.clipBounds.centerY()
            )
        }

        private fun calculateRect(bitmap: Bitmap) {
            rect = Rect(
                center.x - bitmap.width / 2,
                center.y - bitmap.height / 2,
                center.x + bitmap.width / 2,
                center.y + bitmap.height / 2
            )
        }
    }
}

package app.suhocki.tgstickers.editor.step

import android.content.ContentResolver
import android.graphics.*
import android.net.Uri
import androidx.core.graphics.contains

sealed class Step {
    abstract fun draw(canvas: Canvas)
    abstract fun contains(point: Point): Boolean
    abstract fun move(topLeft: Point)

    class AddImage(
        private val uri: Uri,
        private val contentResolver: ContentResolver
    ) : Step() {
        private var rect: Rect? = null
        private var topLeft: Point? = null

        override fun draw(canvas: Canvas) {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            if (topLeft == null) {
                initializeTopLeft(bitmap, canvas)
            }
            calculateRect(bitmap)

            canvas.drawBitmap(bitmap, null, requireNotNull(rect), null)
        }

        override fun contains(point: Point): Boolean {
            return rect?.contains(point) == true
        }

        override fun move(topLeft: Point) {
            this.topLeft = topLeft
        }

        private fun initializeTopLeft(bitmap: Bitmap, canvas: Canvas) {
            val canvasCenter = Point(
                canvas.clipBounds.centerX(),
                canvas.clipBounds.centerY()
            )
            topLeft = Point(
                canvasCenter.x - bitmap.width / 2,
                canvasCenter.y - bitmap.height / 2,
            )
        }

        private fun calculateRect(bitmap: Bitmap) {
            val topLeft = requireNotNull(topLeft)
            rect = Rect(
                topLeft.x,
                topLeft.y,
                topLeft.x + bitmap.width,
                topLeft.y + bitmap.height
            )
        }
    }
}

package app.suhocki.tgstickers.editor.step

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import java.io.InputStream

sealed class Step {
    abstract fun draw(canvas: Canvas)

    class AddImage(
        private val inputStream: InputStream
    ) : Step() {

        override fun draw(canvas: Canvas) {
            val bitmap = BitmapFactory.decodeStream(inputStream)

            canvas.drawBitmap(
                bitmap,
                null,
                Rect(0, 0, bitmap.width, bitmap.height),
                null
            )
        }
    }
}

package app.suhocki.tgstickers.editor.step

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri

sealed class Step {
    abstract fun draw(canvas: Canvas)

    class AddImage(
        private val uri: Uri,
        private val contentResolver: ContentResolver
    ) : Step() {

        override fun draw(canvas: Canvas) {
            val inputStream = contentResolver.openInputStream(uri)
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

package app.suhocki.tgstickers.editor

import android.content.ContentResolver
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.editor.step.Step
import kotlinx.coroutines.flow.MutableStateFlow
import toothpick.InjectConstructor


@InjectConstructor
class Editor(
    private val steps: MutableStateFlow<ArrayDeque<Step>>
) {
    val movement = Movement()

    fun addImage(uri: Uri?, contentResolver: ContentResolver) {
        if (uri == null) {
            return
        }

        val inputStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        val step = Step.AddImage(bitmap)
        val data = ArrayDeque(steps.value + step)

        steps.tryEmit(data)

        movement.selected = step
    }

    fun addText(text: String, resources: Resources) {
        val textSize = resources.getDimensionPixelSize(R.dimen.sp_36).toFloat()
        val bitmap = textAsBitmap(text, textSize, Color.MAGENTA)

        val step = Step.AddImage(bitmap)
        val data = ArrayDeque(steps.value + step)

        steps.tryEmit(data)

        movement.selected = step
    }

    private fun textAsBitmap(text: String, textSize: Float, textColor: Int): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        val baseline: Float = -paint.ascent() // ascent() is negative
        val width = (paint.measureText(text) + 0.5f).toInt()
        val height = (baseline + paint.descent() + 0.5f).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        canvas.drawText(text, 0f, baseline, paint)
        return image
    }

    inner class Movement {
        private var deltaCenter = Point(0, 0)

        var selected: Step? = null

        fun start(point: Point) {
            selected = steps.value
                .reversed()
                .firstOrNull { step -> step.contains(point) }
                ?.also { selected ->
                    deltaCenter = Point(
                        selected.center.x - point.x,
                        selected.center.y - point.y
                    )
                    steps.value.remove(selected)
                    steps.value.add(selected)
                }
        }

        fun move(point: Point) {
            val center = Point(
                point.x + deltaCenter.x,
                point.y + deltaCenter.y,
            )
            selected?.move(center)
        }
    }
}

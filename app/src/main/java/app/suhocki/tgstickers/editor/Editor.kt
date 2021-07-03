package app.suhocki.tgstickers.editor

import android.content.ContentResolver
import android.graphics.Point
import android.net.Uri
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

        val step = Step.AddImage(uri, contentResolver)
        val data = ArrayDeque(steps.value + step)

        steps.tryEmit(data)
    }

    inner class Movement {
        private var selected: Step? = null
        private var deltaCenter = Point(0, 0)

        fun start(point: Point) {
            selected = steps.value
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

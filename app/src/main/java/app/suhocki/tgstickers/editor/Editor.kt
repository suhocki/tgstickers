package app.suhocki.tgstickers.editor

import android.content.ContentResolver
import android.net.Uri
import app.suhocki.tgstickers.editor.step.Step
import kotlinx.coroutines.flow.MutableStateFlow
import toothpick.InjectConstructor

@InjectConstructor
class Editor(
    private val steps: MutableStateFlow<ArrayDeque<Step>>
) {
    fun addImage(uri: Uri?, contentResolver: ContentResolver) {
        if (uri == null) {
            return
        }

        val step = Step.AddImage(uri, contentResolver)
        val data = ArrayDeque(steps.value + step)

        steps.tryEmit(data)
    }

}

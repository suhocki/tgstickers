package app.suhocki.tgstickers.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.suhocki.tgstickers.editor.backgroundRemover.BackgroundRemover
import app.suhocki.tgstickers.editor.step.Step
import app.suhocki.tgstickers.global.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import toothpick.InjectConstructor

@InjectConstructor
class EditorViewModel(
    private val backgroundRemover: BackgroundRemover,
    val errorHandler: ErrorHandler,
) : ViewModel() {
    val removeBackgroundProgress: Flow<Float> = backgroundRemover.progress

    private val _suggestTokenUpdate = MutableSharedFlow<Unit>()
    val suggestTokenUpdate: Flow<Unit> = _suggestTokenUpdate

    fun removeBackground(addImage: Step.AddImage) {
        viewModelScope.launch {
            runCatching {
                backgroundRemover.removeBackground(addImage.bitmap)
            }.onSuccess { bitmap ->
                addImage.bitmap = bitmap
            }.onFailure {
                if (isFreeAccessDenied(it)) {
                    _suggestTokenUpdate.emit(Unit)
                } else {
                    errorHandler.onError(it)
                }
            }
        }
    }

    companion object {
        private fun isFreeAccessDenied(throwable: Throwable): Boolean {
            return throwable.message?.run {
                contains("402")
                contains("Invalid api key")
            } == true
        }
    }

}
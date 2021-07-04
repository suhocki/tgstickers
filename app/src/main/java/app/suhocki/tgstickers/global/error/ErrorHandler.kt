package app.suhocki.tgstickers.global.error

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import toothpick.InjectConstructor

@InjectConstructor
class ErrorHandler(
    private val context: Context
) {
    fun onError(error: Throwable) {
        _errorText.tryEmit(error.message ?: error.toString())
    }

    private val _errorText = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )

    val errorText: Flow<String> = _errorText
}
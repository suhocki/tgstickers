package app.suhocki.tgstickers.editor.backgroundRemover

import android.graphics.Bitmap
import com.slazzer.bgremover.Slazzer
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import toothpick.InjectConstructor
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@InjectConstructor
class BackgroundRemover {
    private val _progress = MutableStateFlow(ProgressState.END.amount)
    val progress: Flow<Float> = _progress

    suspend fun removeBackground(
        bitmap: Bitmap
    ): Bitmap = suspendCancellableCoroutine { continuation ->

        fun upload(file: File) {
            Slazzer.get(file, object : Slazzer.ResponseCallback {
                override fun onError(errors: String) {
                    if (continuation.isActive) {
                        continuation.resumeWithException(
                            BackgroundRemoverException("Error during upload: $errors")
                        )
                    }
                }

                override fun onProgressEnd() {
                    _progress.tryEmit(ProgressState.END.amount)
                }

                override fun onProgressStart() {
                    _progress.tryEmit(ProgressState.INDETERMINATE.amount)
                }

                override fun onProgressUpdate(percentage: Float) {
                    _progress.tryEmit(percentage)
                }

                override fun onSuccess(response: Bitmap) {
                    continuation.resume(response)
                }
            })
        }

        runCatching { bitmap.toFile() }
            .onFailure { continuation.resumeWithException(it) }
            .onSuccess { upload(it) }
    }

    companion object {
        fun Bitmap.toFile(): File {
            val file = File.createTempFile("background_remover_", ".png")
            val outputStream = FileOutputStream(file)

            val success = compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            if (!success) {
                throw BackgroundRemoverException("Bitmap compress error.")
            }

            outputStream.flush()
            outputStream.close()

            return file
        }
    }
}
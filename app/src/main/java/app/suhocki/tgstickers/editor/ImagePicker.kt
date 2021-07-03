package app.suhocki.tgstickers.editor

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class ImagePicker(
    registryOwner: ActivityResultRegistryOwner,
    lifecycleOwner: LifecycleOwner,
) : LifecycleObserver {
    private var callback: ((Uri) -> Unit)? = null

    private val getContent: ActivityResultLauncher<String> =
        registryOwner.activityResultRegistry.register(
            this::class.toString(),
            lifecycleOwner,
            ActivityResultContracts.GetContent()
        ) {
            callback?.invoke(it)
        }

    fun pickImage() {
        getContent.launch("image/*")
    }

    fun subscribe(
        lifecycleOwner: LifecycleOwner,
        callback: (Uri) -> Unit
    ) {
        lifecycleOwner.lifecycle.addObserver(this)
        this.callback = callback
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun tearDown() {
        callback = null
    }
}
package app.suhocki.tgstickers.edit

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner

class ImagePicker(
    activityResultRegistry: ActivityResultRegistry,
    lifecycleOwner: LifecycleOwner,
    callback: (imageUri: Uri?) -> Unit,
) {
    private val getContent: ActivityResultLauncher<String> =
        activityResultRegistry.register(
            this::class.toString(), lifecycleOwner,
            ActivityResultContracts.GetContent(), callback
        )

    fun pickImage() {
        getContent.launch("image/*")
    }
}
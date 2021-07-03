package app.suhocki.tgstickers.editor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.FragmentEditorBinding
import app.suhocki.tgstickers.editor.step.Step
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

@InjectConstructor
class EditorFragment(
    private val steps: StateFlow<List<Step>>,
    private val editor: Editor,
    private val imagePicker: ImagePicker
) : Fragment(R.layout.fragment_editor) {
    private val viewBinding: FragmentEditorBinding by viewBinding()

    private val drawer: Drawer by lazy {
        Drawer(lifecycleScope, viewBinding.surfaceView, steps)
    }

    init {
        imagePicker.subscribe(this) { uri ->
            editor.addImage(uri, requireContext().contentResolver)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openScope(EditorFragment::class)
            .closeOnViewModelCleared(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addPicture.setOnClickListener {
            imagePicker.pickImage()
        }
        drawer
    }
}
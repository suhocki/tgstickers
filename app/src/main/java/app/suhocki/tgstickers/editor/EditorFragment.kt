package app.suhocki.tgstickers.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.TgStickersActivity
import app.suhocki.tgstickers.databinding.FragmentEditorBinding
import app.suhocki.tgstickers.editor.step.Step
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.StateFlow
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

@InjectConstructor
class EditorFragment(
    private val steps: StateFlow<List<Step>>,
    private val editor: Editor,
) : Fragment(R.layout.fragment_editor) {
    private val viewBinding: FragmentEditorBinding by viewBinding()
    private var imagePicker: ImagePicker? = null

    private val gestureRecognizer = GestureRecognizer(
        move = { from, to ->
            editor.move(from, to)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openScope(EditorFragment::class)
            .closeOnViewModelCleared(this)
        initImagePicker()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addPicture.setOnClickListener {
            imagePicker?.pickImage()
        }
        viewBinding.surfaceView.setOnTouchListener { _, event ->
            gestureRecognizer.handle(event)
            true
        }
        Drawer(lifecycleScope, viewBinding.surfaceView, steps)
    }

    private fun initImagePicker() {
        imagePicker = (requireActivity() as TgStickersActivity).imagePicker
        imagePicker?.subscribe(this) { uri ->
            editor.addImage(uri, requireContext().contentResolver)
        }
    }
}
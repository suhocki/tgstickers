package app.suhocki.tgstickers.editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.TgStickersActivity
import app.suhocki.tgstickers.databinding.FragmentEditorBinding
import app.suhocki.tgstickers.editor.step.Step
import app.suhocki.tgstickers.editor.textPicker.TextPickerDialog
import app.suhocki.tgstickers.editor.textPicker.textPickerModule
import app.suhocki.tgstickers.editor.tokenUpdate.TokenUpdateDialog
import app.suhocki.tgstickers.global.navigation.navigateSafe
import app.suhocki.tgstickers.global.prefs.Prefs
import app.suhocki.tgstickers.global.snackbar.showSnackbar
import app.suhocki.tgstickers.global.viewmodel.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.slazzer.bgremover.Slazzer
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared


@InjectConstructor
class EditorFragment(
    private val steps: StateFlow<List<Step>>,
    private val editor: Editor,
    private val prefs: Prefs,
) : Fragment(R.layout.fragment_editor) {
    private val viewBinding: FragmentEditorBinding by viewBinding()
    private val viewModel: EditorViewModel by viewModel()
    private var imagePicker: ImagePicker? = null
    private var textFlow = MutableSharedFlow<String>(
        onBufferOverflow = BufferOverflow.DROP_LATEST,
        extraBufferCapacity = 1
    )
    private var drawer: Drawer? = null

    private val gestureRecognizer = GestureRecognizer(
        start = { point ->
            editor.movement.start(point)
            updateActions()
        },
        move = { point ->
            editor.movement.move(point)
        },
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openScope(EditorFragment::class)
            .closeOnViewModelCleared(this)
        initImagePicker()
        initTextPicker()
        initViewModelObservers()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addPicture.setOnClickListener {
            imagePicker?.pickImage()
        }
        viewBinding.addText.setOnClickListener {
            KTP.openScopes(EditorFragment::class, TextPickerDialog::class)
                .installModules(textPickerModule(textFlow))
            findNavController().navigateSafe(
                EditorFragmentDirections.actionEditorFragmentToTextPickerDialog()
            )
        }
        viewBinding.magicButton.setOnClickListener {
            val addImage = editor.movement.selected as? Step.AddImage
            if (addImage != null) {
                Slazzer.init(prefs.slazzerToken)
                viewModel.removeBackground(addImage)
            }
        }
        viewBinding.surfaceView.setOnTouchListener { _, event ->
            gestureRecognizer.handle(event)
            true
        }
        viewBinding.exit.setOnClickListener {
            findNavController().navigateUp()
        }
        viewBinding.done.setOnClickListener {
            showSnackbar("Not implemented")
        }

        drawer = Drawer(lifecycleScope, viewBinding.surfaceView, steps)

        updateActions()
    }

    private fun initImagePicker() {
        imagePicker = (requireActivity() as TgStickersActivity).imagePicker
        imagePicker?.subscribe(this) { uri ->
            editor.addImage(uri, requireContext().contentResolver)
            updateActions()
        }
    }

    private fun initTextPicker() {
        lifecycleScope.launch {
            textFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { text ->
                    editor.addText(text, resources)
                }
        }
    }

    private fun initViewModelObservers() {
        lifecycleScope.launch {
            viewModel.errorHandler.errorText
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { text -> showSnackbar(text) }
        }
        lifecycleScope.launch {
            viewModel.removeBackgroundProgress
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { progress ->
                    viewBinding.magicButton.progress = progress
                }
        }
        lifecycleScope.launch {
            viewModel.suggestTokenUpdate
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    KTP.openScopes(EditorFragment::class, TokenUpdateDialog::class)
                    findNavController().navigateSafe(
                        EditorFragmentDirections.actionEditorFragmentToTokenUpdateDialog()
                    )
                }
        }
    }

    private fun updateActions() {
        viewBinding.magicButton.isEnabled = editor.movement.selected is Step.AddImage
    }
}
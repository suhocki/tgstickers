package app.suhocki.tgstickers.editor.textPicker

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.DialogTextPickerBinding
import app.suhocki.tgstickers.global.fragment.showKeyboard
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import toothpick.InjectConstructor

@InjectConstructor
class TextPickerDialog(
    private val textFlow: MutableSharedFlow<String>
) : DialogFragment(R.layout.dialog_text_picker) {
    private val viewBinding: DialogTextPickerBinding by viewBinding()

    private val text: String
        get() = viewBinding.editText.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.editText.doAfterTextChanged {
            val text = it.toString()
            viewBinding.done.isEnabled = text.isNotBlank()
        }
        viewBinding.done.setOnClickListener {
            findNavController().navigateUp()
            textFlow.tryEmit(text)
        }
        showKeyboard(viewBinding.editText)
    }
}
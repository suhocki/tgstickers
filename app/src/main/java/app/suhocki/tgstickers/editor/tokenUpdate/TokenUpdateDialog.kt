package app.suhocki.tgstickers.editor.tokenUpdate

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.DialogTokenUpdateBinding
import app.suhocki.tgstickers.global.prefs.Prefs
import by.kirich1409.viewbindingdelegate.viewBinding
import toothpick.InjectConstructor

@InjectConstructor
class TokenUpdateDialog(
    private val prefs: Prefs
) : DialogFragment(R.layout.dialog_token_update) {
    private val viewBinding: DialogTokenUpdateBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.editText.doAfterTextChanged {
            val token = it.toString()
            viewBinding.apply.isEnabled = token.length == TOKEN_LENGTH
        }
        viewBinding.apply.setOnClickListener {
            prefs.slazzerToken = viewBinding.editText.text.toString()
            findNavController().navigateUp()
        }
        viewBinding.cancel.setOnClickListener {
            findNavController().navigateUp()
        }
        viewBinding.description.movementMethod = LinkMovementMethod.getInstance()
    }

    companion object {
        private const val TOKEN_LENGTH = 32
    }
}
package app.suhocki.tgstickers.global.snackbar

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.R as MaterialR

fun Fragment.showSnackbar(text: String) {
    val parent = if (this is BottomSheetDialogFragment) {
        requireNotNull(requireDialog().window).decorView
    } else {
        requireView()
    }
    Snackbar.make(parent, text, Snackbar.LENGTH_LONG).apply {
        val textView = view.findViewById<TextView>(MaterialR.id.snackbar_text)
        textView.isSingleLine = false
    }.show()
}

fun Fragment.showSnackbar(@StringRes textRes: Int) = showSnackbar(getString(textRes))

package app.suhocki.tgstickers.global.fragment

import android.annotation.SuppressLint
import android.view.View
import com.google.android.material.internal.ViewUtils

@SuppressLint("RestrictedApi")
fun showKeyboard(editText: View) {
    ViewUtils.requestFocusAndShowKeyboard(editText)
}
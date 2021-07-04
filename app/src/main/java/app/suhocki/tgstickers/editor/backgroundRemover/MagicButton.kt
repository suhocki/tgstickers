package app.suhocki.tgstickers.editor.backgroundRemover

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.ViewMagicButtonBinding

class MagicButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyle, defStyleRes) {

    private val viewBinding = ViewMagicButtonBinding.bind(
        LayoutInflater.from(context)
            .inflate(R.layout.view_magic_button, this, true)
    )

    var progress: Float = ProgressState.END.amount
        set(value) {
            field = value
            refreshState()
        }

    override fun setOnClickListener(listener: OnClickListener?) {
        viewBinding.imageView.setOnClickListener(listener)
    }

    @SuppressLint("SetTextI18n")
    private fun refreshState() {
        with(viewBinding) {
            imageView.isInvisible = progress < ProgressState.END.amount
            progressBar.isVisible = progress < ProgressState.END.amount

            textView.isGone = progress in setOf(
                ProgressState.INDETERMINATE.amount,
                ProgressState.END.amount
            )
            textView.text = "${progress.toInt()}%"
        }
    }
}

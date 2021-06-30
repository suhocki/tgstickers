package app.suhocki.tgstickers.stickers.add

import app.suhocki.tgstickers.databinding.ItemAddBinding
import app.suhocki.tgstickers.global.adapter.UiItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun addDelegate(
    onClick: () -> Unit
) = adapterDelegateViewBinding<UiAdd, UiItem, ItemAddBinding>(
    { layoutInflater, root -> ItemAddBinding.inflate(layoutInflater, root, false) }
) {
    itemView.setOnClickListener { onClick() }

    bind {}
}
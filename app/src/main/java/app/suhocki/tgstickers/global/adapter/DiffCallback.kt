package app.suhocki.tgstickers.global.adapter

import androidx.recyclerview.widget.DiffUtil
import app.suhocki.tgstickers.global.adapter.UiItem

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UiItem>() {
    override fun areItemsTheSame(oldItem: UiItem, newItem: UiItem) =
        oldItem.isItemTheSame(newItem)

    override fun areContentsTheSame(oldItem: UiItem, newItem: UiItem) =
        oldItem.isContentTheSame(newItem)

    override fun getChangePayload(oldItem: UiItem, newItem: UiItem) =
        oldItem.getChangePayload(newItem)
}

package app.suhocki.tgstickers.stickers.add

import app.suhocki.tgstickers.global.adapter.UiItem

class UiAdd : UiItem {
    override fun isItemTheSame(newItem: UiItem): Boolean {
        return newItem is UiAdd
    }
}
package app.suhocki.tgstickers.global.adapter

/**
 * Use for generic DiffCallback.
 */
interface UiItem {
    /**
     * Called to check whether two objects represent the same item.
     */
    fun isItemTheSame(newItem: UiItem): Boolean

    /**
     * Called to check whether two items have the same data.
     */
    fun isContentTheSame(newItem: UiItem): Boolean = newItem == this

    /**
     * To show blink animation if not null
     */
    fun getChangePayload(newItem: UiItem): Any? = Any()
}

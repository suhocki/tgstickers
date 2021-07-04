package app.suhocki.tgstickers.editor.backgroundRemover

enum class ProgressState(val amount: Float) {
    INDETERMINATE(-1f),
    END(100f),
}
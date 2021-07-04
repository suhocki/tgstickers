package app.suhocki.tgstickers.editor.textPicker

import kotlinx.coroutines.flow.MutableSharedFlow
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun textPickerModule(textFlow: MutableSharedFlow<String>) = module {
    bind<MutableSharedFlow<*>>().toInstance(textFlow)
}
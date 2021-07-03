package app.suhocki.tgstickers.stickers.module

import app.suhocki.tgstickers.global.adapter.UiItem
import app.suhocki.tgstickers.stickers.add.UiAdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun stickersModule() = module {
    val uiItems = MutableSharedFlow<List<UiItem>>(replay = 1)
    uiItems.tryEmit(listOf(UiAdd()))
    bind<Flow<*>>().toInstance(uiItems)
}
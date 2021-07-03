package app.suhocki.tgstickers.editor

import app.suhocki.tgstickers.editor.step.Step
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun editorModule() = module {
    val steps = MutableStateFlow<ArrayDeque<Step>>(ArrayDeque())

    bind<MutableStateFlow<*>>().toInstance(steps)
    bind<StateFlow<*>>().toInstance(steps)
}
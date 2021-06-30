package app.suhocki.tgstickers.global.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import toothpick.ktp.KTP

class NamedScopeViewModelFactory(
    private val scopeName: Any
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) = if (KTP.isScopeOpen(scopeName)) {
        KTP.openScope(scopeName).getInstance(modelClass) as T
    } else {
        error("Scope $scopeName is closed. Open the scope before touching ViewModel.")
    }
}

class ScopedViewModelFactory(private val scopeName: Any) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        KTP.openScope(scopeName).getInstance(modelClass) as T
}

inline fun <reified T : ViewModel> Fragment.viewModel(): Lazy<T> = lazy {
    ViewModelProvider(this, NamedScopeViewModelFactory(this::class)).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModel(crossinline scopeName: () -> Any): Lazy<T> =
    lazy {
        ViewModelProvider(this, ScopedViewModelFactory(scopeName())).get(T::class.java)
    }

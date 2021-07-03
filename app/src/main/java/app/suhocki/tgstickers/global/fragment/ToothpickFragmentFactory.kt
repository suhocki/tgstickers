package app.suhocki.tgstickers.global.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import app.suhocki.tgstickers.global.string.toClass
import toothpick.ktp.KTP

class ToothpickFragmentFactory : FragmentFactory() {
    override fun instantiate(
        classLoader: ClassLoader,
        className: String
    ) = if (KTP.isScopeOpen(className.toClass())) {
        runCatching {
            KTP.openScope(className.toClass()).getInstance(Class.forName(className)) as Fragment
        }.recover {
            super.instantiate(classLoader, className)
        }.getOrThrow()
    } else {
        super.instantiate(classLoader, className)
    }
}
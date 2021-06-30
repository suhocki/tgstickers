package app.suhocki.tgstickers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import app.suhocki.tgstickers.global.string.toClass
import toothpick.Toothpick
import toothpick.ktp.KTP

class TgStickersActivity : AppCompatActivity(R.layout.activity_tgstickers) {
    private val fragmentFactory = object : FragmentFactory() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        if (KTP.isScopeOpen(Toothpick::class.java)) {
            super.onCreate(savedInstanceState)
        } else {
            initToothpick()
            super.onCreate(null)
        }
    }

    private fun initToothpick() = KTP.openRootScope()
}
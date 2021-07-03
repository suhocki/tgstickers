package app.suhocki.tgstickers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import app.suhocki.tgstickers.global.fragment.ToothpickFragmentFactory
import app.suhocki.tgstickers.global.string.toClass
import toothpick.Toothpick
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class TgStickersActivity : AppCompatActivity(R.layout.activity_tgstickers) {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = ToothpickFragmentFactory()

        fun initToothpick() {
            KTP.openRootScope()
                .openSubScope(TgStickersActivity::class) {
                    it.installModules(activityModule(this))
                }
                .closeOnViewModelCleared(this)
        }

        if (KTP.isScopeOpen(Toothpick::class.java)) {
            initToothpick()
            super.onCreate(savedInstanceState)
        } else {
            initToothpick()
            super.onCreate(null)
        }
    }
}
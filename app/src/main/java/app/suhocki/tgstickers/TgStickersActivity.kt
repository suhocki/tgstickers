package app.suhocki.tgstickers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.suhocki.tgstickers.editor.ImagePicker
import app.suhocki.tgstickers.global.fragment.ToothpickFragmentFactory
import toothpick.Toothpick
import toothpick.ktp.KTP
import toothpick.ktp.binding.module
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class TgStickersActivity : AppCompatActivity(R.layout.activity_tgstickers) {
    var imagePicker: ImagePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = ToothpickFragmentFactory()
        imagePicker = ImagePicker(this, this)

        fun initToothpick() {
            KTP.openRootScope()
                .openSubScope(TgStickersActivity::class)
                .closeOnViewModelCleared(this)
                .installModules(activityModule(applicationContext))
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
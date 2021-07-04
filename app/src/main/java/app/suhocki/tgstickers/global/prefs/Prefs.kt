package app.suhocki.tgstickers.global.prefs

import android.content.SharedPreferences
import androidx.core.content.edit
import toothpick.InjectConstructor

@InjectConstructor
class Prefs(private val sharedPreferences: SharedPreferences) {

    var slazzerToken: String
        get() = sharedPreferences.getString(SLAZZER_TOKEN, null) ?: DEFAULT_SLAZZER_TOKEN
        set(value) {
            sharedPreferences.edit {
                putString(SLAZZER_TOKEN, value)
            }
        }

    private companion object Keys {
        private const val SLAZZER_TOKEN = "SLAZZER_TOKEN"

        private const val DEFAULT_SLAZZER_TOKEN = "37175c1841c5499898fc635b4fef2e7a"
    }
}

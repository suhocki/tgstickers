package app.suhocki.tgstickers.global.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Provider
import toothpick.InjectConstructor

@InjectConstructor
class SharedPreferencesProvider(
    private val context: Context
) : Provider<SharedPreferences> {

    override fun get(): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE,
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    private companion object {
        const val PREFS_FILE = "tg-stickers-values"
    }
}

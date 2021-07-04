package app.suhocki.tgstickers

import android.content.Context
import android.content.SharedPreferences
import app.suhocki.tgstickers.global.prefs.SharedPreferencesProvider
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun activityModule(context: Context) = module {
    bind<Context>().toInstance(context)
    bind<SharedPreferences>().toProvider(SharedPreferencesProvider::class).providesSingleton()

}
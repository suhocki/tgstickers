package app.suhocki.tgstickers

import androidx.appcompat.app.AppCompatActivity
import app.suhocki.tgstickers.editor.ImagePicker
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module

fun activityModule(activity: AppCompatActivity) = module {
    val imagePicker = ImagePicker(activity, activity)

    bind<ImagePicker>().toInstance(imagePicker)
}
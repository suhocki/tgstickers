package app.suhocki.tgstickers.start

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.TgStickersActivity
import app.suhocki.tgstickers.databinding.FragmentStartBinding
import app.suhocki.tgstickers.global.navigation.navigateSafe
import app.suhocki.tgstickers.stickers.StickersFragment
import app.suhocki.tgstickers.stickers.module.stickersModule
import by.kirich1409.viewbindingdelegate.viewBinding
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

class StartFragment : Fragment(R.layout.fragment_start) {
    private val viewBinding: FragmentStartBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openScopes(TgStickersActivity::class, StartFragment::class)
            .closeOnViewModelCleared(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.start.setOnClickListener {
            KTP.openScopes(StartFragment::class, StickersFragment::class)
                .installModules(stickersModule())
            findNavController().navigateSafe(
                StartFragmentDirections.actionStartFragmentToStickersFragment()
            )
        }
    }
}
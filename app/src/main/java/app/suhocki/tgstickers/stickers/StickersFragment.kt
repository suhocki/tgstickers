package app.suhocki.tgstickers.stickers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.FragmentStickersBinding
import app.suhocki.tgstickers.editor.EditorFragment
import app.suhocki.tgstickers.editor.editorModule
import app.suhocki.tgstickers.global.adapter.UiItem
import app.suhocki.tgstickers.global.adapter.UiItemAdapter
import app.suhocki.tgstickers.global.navigation.navigateSafe
import app.suhocki.tgstickers.stickers.add.addDelegate
import by.kirich1409.viewbindingdelegate.viewBinding
import com.slazzer.bgremover.Slazzer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import toothpick.InjectConstructor
import toothpick.ktp.KTP
import toothpick.smoothie.viewmodel.closeOnViewModelCleared

@InjectConstructor
class StickersFragment(
    private val uiItems: Flow<List<UiItem>>
) : Fragment(R.layout.fragment_stickers) {
    private val viewBinding: FragmentStickersBinding by viewBinding()

    private val adapter = UiItemAdapter(
        addDelegate {
            KTP.openScopes(StickersFragment::class, EditorFragment::class)
                .installModules(editorModule())

            findNavController().navigateSafe(
                StickersFragmentDirections.actionStickersFragmentToEditorFragment()
            )
        }
    )

    init {
        lifecycleScope.launchWhenStarted {
            uiItems.collect { items ->
                adapter.items = items
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KTP.openScope(StickersFragment::class)
            .closeOnViewModelCleared(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), GRID_SIZE)
        }
    }

    companion object {
        private const val GRID_SIZE = 3
    }
}
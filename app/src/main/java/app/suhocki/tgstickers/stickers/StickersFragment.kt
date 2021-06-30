package app.suhocki.tgstickers.stickers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import app.suhocki.tgstickers.R
import app.suhocki.tgstickers.databinding.FragmentStickersBinding
import app.suhocki.tgstickers.global.adapter.UiItem
import app.suhocki.tgstickers.global.adapter.UiItemAdapter
import app.suhocki.tgstickers.stickers.add.addDelegate
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import toothpick.InjectConstructor

@InjectConstructor
class StickersFragment(
    private val uiItems: Flow<List<UiItem>>
) : Fragment(R.layout.fragment_stickers) {
    private val viewBinding: FragmentStickersBinding by viewBinding()

    private val adapter = UiItemAdapter(
        addDelegate { }
    )

    init {
        lifecycleScope.launchWhenStarted {
            uiItems.collect { items ->
                adapter.items = items
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }
}
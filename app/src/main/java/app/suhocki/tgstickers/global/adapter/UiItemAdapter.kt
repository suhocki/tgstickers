package app.suhocki.tgstickers.global.adapter

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class UiItemAdapter : AsyncListDifferDelegationAdapter<UiItem> {
    constructor(
        adapterDelegates: List<AdapterDelegate<List<UiItem>>>,
    ) : super(DIFF_CALLBACK) {
        adapterDelegates.forEach { delegatesManager.addDelegate(it) }
    }

    constructor(
        vararg adapterDelegates: AdapterDelegate<List<UiItem>>,
    ) : this(adapterDelegates.toList())
}

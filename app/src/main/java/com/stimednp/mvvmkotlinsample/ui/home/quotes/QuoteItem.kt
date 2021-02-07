package com.stimednp.mvvmkotlinsample.ui.home.quotes

import com.stimednp.mvvmkotlinsample.R
import com.stimednp.mvvmkotlinsample.data.db.entities.Quote
import com.stimednp.mvvmkotlinsample.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote:Quote
) : BindableItem<ItemQuoteBinding>() {
    override fun getLayout()= R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.quote = quote
    }

}
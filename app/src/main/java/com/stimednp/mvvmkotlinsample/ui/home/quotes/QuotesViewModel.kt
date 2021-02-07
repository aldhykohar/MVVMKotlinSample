package com.stimednp.mvvmkotlinsample.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.stimednp.mvvmkotlinsample.data.repository.QuotesRepository
import com.stimednp.mvvmkotlinsample.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}
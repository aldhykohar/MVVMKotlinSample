package com.stimednp.mvvmkotlinsample.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stimednp.mvvmkotlinsample.data.repository.QuotesRepository
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory (
    private val respository:QuotesRepository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(respository) as T
    }
}
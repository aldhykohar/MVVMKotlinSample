package com.stimednp.mvvmkotlinsample.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory (
    private val respository:UserRepository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(respository) as T
    }
}
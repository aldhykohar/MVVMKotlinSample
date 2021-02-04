package com.stimednp.mvvmkotlinsample.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory (
    private val respository:UserRepository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(respository) as T
    }
}
package com.stimednp.mvvmkotlinsample.ui.home.profile

import androidx.lifecycle.ViewModel
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}
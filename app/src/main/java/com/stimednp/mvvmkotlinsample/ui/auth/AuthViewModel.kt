package com.stimednp.mvvmkotlinsample.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository
import com.stimednp.mvvmkotlinsample.ui.util.ApiException
import com.stimednp.mvvmkotlinsample.ui.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(authResponse.user)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            }

        }

    }
}
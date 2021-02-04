package com.stimednp.mvvmkotlinsample.ui.auth

import com.stimednp.mvvmkotlinsample.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}
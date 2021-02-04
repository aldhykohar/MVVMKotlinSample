package com.stimednp.mvvmkotlinsample.data.network.response

import com.stimednp.mvvmkotlinsample.data.db.entities.User

data class AuthResponse(
    val isSuccesful: Boolean?,
    val message: String?,
    val user: User?
)
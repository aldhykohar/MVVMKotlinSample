package com.stimednp.mvvmkotlinsample.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stimednp.mvvmkotlinsample.R
import com.stimednp.mvvmkotlinsample.databinding.ActivityLoginBinding
import com.stimednp.mvvmkotlinsample.ui.util.hide
import com.stimednp.mvvmkotlinsample.ui.util.show
import com.stimednp.mvvmkotlinsample.ui.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(loginResponse: LiveData<String>) {
        loginResponse.observe(this, Observer {
            binding.progressBar.hide()
            toast(it)
        })
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        toast(message)
    }
}
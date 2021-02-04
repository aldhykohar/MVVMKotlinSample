package com.stimednp.mvvmkotlinsample.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stimednp.mvvmkotlinsample.R
import com.stimednp.mvvmkotlinsample.data.db.AppDatabase
import com.stimednp.mvvmkotlinsample.data.db.entities.User
import com.stimednp.mvvmkotlinsample.data.network.MyApi
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository
import com.stimednp.mvvmkotlinsample.databinding.ActivityLoginBinding
import com.stimednp.mvvmkotlinsample.ui.home.HomeActivity
import com.stimednp.mvvmkotlinsample.ui.util.hide
import com.stimednp.mvvmkotlinsample.ui.util.show
import com.stimednp.mvvmkotlinsample.ui.util.toast

class LoginActivity : AppCompatActivity(), AuthListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDatabase(this)
        val repository = UserRepository(api, db)
        val factory = AuthViewModelFactory(repository)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        })

    }

    override fun onStarted() {
        binding.progressBar.show()
    }

    override fun onSuccess(user: User) {
        binding.progressBar.hide()
        toast("${user.name} is Logged In")
    }

    override fun onFailure(message: String) {
        binding.progressBar.hide()
        toast(message)
    }
}
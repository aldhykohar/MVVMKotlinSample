package com.stimednp.mvvmkotlinsample

import android.app.Application
import com.stimednp.mvvmkotlinsample.data.db.AppDatabase
import com.stimednp.mvvmkotlinsample.data.network.MyApi
import com.stimednp.mvvmkotlinsample.data.network.NetworkConnectionInterceptor
import com.stimednp.mvvmkotlinsample.data.preferences.PreferencesProvider
import com.stimednp.mvvmkotlinsample.data.repository.QuotesRepository
import com.stimednp.mvvmkotlinsample.data.repository.UserRepository
import com.stimednp.mvvmkotlinsample.ui.auth.AuthViewModelFactory
import com.stimednp.mvvmkotlinsample.ui.home.profile.ProfileViewModelFactory
import com.stimednp.mvvmkotlinsample.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferencesProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }
    }
}
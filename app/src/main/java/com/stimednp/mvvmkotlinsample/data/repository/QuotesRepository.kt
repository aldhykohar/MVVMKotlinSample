package com.stimednp.mvvmkotlinsample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stimednp.mvvmkotlinsample.data.db.AppDatabase
import com.stimednp.mvvmkotlinsample.data.db.entities.Quote
import com.stimednp.mvvmkotlinsample.data.network.MyApi
import com.stimednp.mvvmkotlinsample.data.network.SafeApiRequest
import com.stimednp.mvvmkotlinsample.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever {
            Log.e("DATA", "$it ")
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        if (isFeetchNeeded()) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFeetchNeeded(): Boolean {
        return true
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}
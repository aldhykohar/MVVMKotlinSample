package com.stimednp.mvvmkotlinsample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stimednp.mvvmkotlinsample.data.db.AppDatabase
import com.stimednp.mvvmkotlinsample.data.db.entities.Quote
import com.stimednp.mvvmkotlinsample.data.network.MyApi
import com.stimednp.mvvmkotlinsample.data.network.SafeApiRequest
import com.stimednp.mvvmkotlinsample.data.preferences.PreferencesProvider
import com.stimednp.mvvmkotlinsample.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

private val MINIMUM_INTERVAL = 6

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferencesProvider
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
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFeetchNeeded(lastSavedAt)) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFeetchNeeded(savedAt: String?): Boolean {
        Log.e("DATA", "isFeetchNeeded: " + getDiffTime(savedAt))
        return getDiffTime(savedAt) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {

        Coroutines.io {
            prefs.saveLastSavedAt(getCurrentTime())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

    private fun getCurrentTime(): String {
        val dateFormat =
            SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun getDiffTime(savedAt: String?): Long {
        val dateFormat =
            SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
        val firstDate: Date = dateFormat.parse(savedAt)
        val secondDate: Date = dateFormat.parse(getCurrentTime())
        val diffInMillies: Long =
            abs(secondDate.time - firstDate.time)
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)
    }
}
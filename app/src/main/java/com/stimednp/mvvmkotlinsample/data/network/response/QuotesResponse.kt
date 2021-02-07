package com.stimednp.mvvmkotlinsample.data.network.response

import com.stimednp.mvvmkotlinsample.data.db.entities.Quote

data class QuotesResponse(
    val isSuccesful: Boolean,
    val quotes: List<Quote>
)
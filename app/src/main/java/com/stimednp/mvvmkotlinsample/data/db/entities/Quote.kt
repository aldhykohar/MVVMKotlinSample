package com.stimednp.mvvmkotlinsample.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String,
    val author: String,
    val thumbnail: String? =null,
    val created_at: String? = null,
    val updated_at: String? = null
)
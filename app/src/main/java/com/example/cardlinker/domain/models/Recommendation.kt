package com.example.cardlinker.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommendation")
data class Recommendation(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
    ,
    val marketName: String,
    val marketImage: Int,
    val reference: String
)
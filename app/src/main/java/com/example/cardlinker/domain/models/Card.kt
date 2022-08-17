package com.example.cardlinker.domain.models

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String?,
    val number: String?,
    val code: Code,
    val background: Int,
    @Embedded
    val style: Style?
)
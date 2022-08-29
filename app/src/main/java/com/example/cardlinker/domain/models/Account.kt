package com.example.cardlinker.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nickname: String,
    val email: String,
    val encodedPassword: String,
    val cardCodes: List<Code>
) {
    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + nickname.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + encodedPassword.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false
        if (nickname != other.nickname) return false
        if (email != other.email) return false
        if (encodedPassword != other.encodedPassword) return false
        if (cardCodes != other.cardCodes) return false

        return true
    }
}
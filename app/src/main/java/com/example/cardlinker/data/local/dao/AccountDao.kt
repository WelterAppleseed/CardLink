package com.example.cardlinker.data.local.dao

import androidx.room.*
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.models.Recommendation
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAccount(account: Account)
    @Query("UPDATE account SET nickname =:newNick AND email =:newEmail AND encodedPassword =:newEncodedPassword WHERE encodedPassword=:oldEncodedPassword")
    suspend fun updateAccount(newNick: String, newEmail: String, newEncodedPassword: String, oldEncodedPassword: String)
    @Query("SELECT * FROM account WHERE encodedPassword =:encodedPassword AND (nickname=:nickOrEmail OR email=:nickOrEmail)")
    fun loginAccount(nickOrEmail: String,encodedPassword: String): Flow<Account>
    @Query("SELECT * FROM account WHERE encodedPassword=:encodedPassword")
    fun getAccount(encodedPassword: String): Flow<Account>
    @Query("SELECT * FROM account")
    fun getAll(): Flow<List<Account>>
    @Query("DELETE FROM account WHERE id=:id")
    fun deleteAccount(id: Int)
}
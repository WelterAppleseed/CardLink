package com.example.cardlinker.data.local.dao

import androidx.room.*
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.models.Recommendation
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAccount(account: Account)
    @Query("UPDATE account SET encodedPassword =:newEncodedPassword WHERE email=:email")
    suspend fun updateAccountPassword(email: String, newEncodedPassword: String)
    @Query("UPDATE account SET nickname=:nickname, email=:email  WHERE id=:id")
    suspend fun updateAccountData(id: Long, nickname: String, email: String)
    @Query("SELECT * FROM account WHERE encodedPassword =:encodedPassword AND (nickname=:nickOrEmail OR email=:nickOrEmail)")
    fun loginAccount(nickOrEmail: String,encodedPassword: String): Flow<Account>
    @Query("SELECT * FROM account WHERE email=:email")
    fun getAccount(email: String): Flow<Account>
    @Query("SELECT * FROM account")
    fun getAll(): Flow<List<Account>>
    @Query("SELECT EXISTS(SELECT * FROM account WHERE email=:email)")
    fun isAccountExisted(email: String): Flow<Boolean>
    @Query("DELETE FROM account WHERE email=:email")
    suspend fun deleteAccount(email: String)
}
package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.usecases.*
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val insertAccountUseCase: InsertAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getCurrentEncodedPasswordUseCase: GetCurrentEncodedPasswordUseCase,
    private val insertCurrentEncodedPasswordUseCase: InsertCurrentEncodedPasswordUseCase,
    private val updateAccountDataUseCase: UpdateAccountDataUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val loginAttemptUseCase: AccountLoginAttemptUseCase
) : BaseViewModel() {
    private val encodedPasswordLiveData = MutableLiveData("")
    private val accountLiveData = MutableLiveData<Account?>(null)
    private val isAccountExist = MutableLiveData<Boolean?>(null)
    init {
        encodedPasswordLiveData.value = getCurrentEncodedPasswordUseCase.execute()
        initAccount()
        checkAllAccounts()
    }
    private fun checkAllAccounts() {
        viewModelScope.launch {
            getAllAccountsUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    println(it)
                }
        }
    }

    fun setPassword(encodedPassword: String) {
        viewModelScope.launch {
            insertCurrentEncodedPasswordUseCase.saveInput(encodedPassword)
            insertCurrentEncodedPasswordUseCase.execute()
            encodedPasswordLiveData.value = encodedPassword
        }
    }
    fun getPassword() = encodedPasswordLiveData
    fun setAccount(account: Account) {
        viewModelScope.launch {
            insertAccountUseCase.saveInput(account)
            insertAccountUseCase.execute()
            accountLiveData.value = account
            checkAllAccounts()
        }
    }
    fun logout() {
        println("qwerqwerqwergdfgsdgdfgfd")
        accountLiveData.value = null
        encodedPasswordLiveData.value = null
    }
    fun attemptToLogin(nickname: String, encodedPassword: String) {
        viewModelScope.launch {
            loginAttemptUseCase.saveInput(nickname, encodedPassword)
            loginAttemptUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    accountLiveData.value = it
                    isAccountExist.value = (it != null)
                }
        }
    }
    fun isAccountExist() = isAccountExist
    private fun initAccount() {
        viewModelScope.launch {
            val str = if (encodedPasswordLiveData.value != null && encodedPasswordLiveData.value!!.length > 4) encodedPasswordLiveData.value?.substring(0,
                encodedPasswordLiveData.value?.length?.minus(4) ?: 0
            ) else ""
                getAccountUseCase.saveInput(str?: "")
            getAccountUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    accountLiveData.value = it
                    checkAllAccounts()
                }
        }
    }
    fun getAccount() = accountLiveData
}
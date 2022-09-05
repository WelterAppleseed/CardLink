package com.example.cardlinker.presentation.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.domain.usecases.*
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val insertAccountUseCase: InsertAccountUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    getCurrentEmailUseCase: GetCurrentEmailUseCase,
    private val insertCurrentEmailUseCase: InsertCurrentEmailUseCase,
    private val updateAccountPasswordUseCase: UpdateAccountPasswordUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val loginAttemptUseCase: AccountLoginAttemptUseCase,
    private val isAccountExistUseCase: CheckIsAccountExistUseCase,
    private val updateAccountDataUseCase: UpdateAccountDataUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel() {
    private val currentEmailLiveData = MutableLiveData("")
    private val accountLiveData = MutableLiveData<Account?>(null)
    private val isAccountExist = MutableLiveData<Boolean?>(null)
    private val isAnyAccountExist = MutableLiveData<Boolean>(false)
    private val isAccountAlreadyExist = MutableLiveData<Boolean?>(null)

    init {
        currentEmailLiveData.value = getCurrentEmailUseCase.execute()
        initAccount()
        checkAllAccounts("init")
        //TODO удалить чекАлл повсюду, просто для удобства пока-что
    }

    private fun checkAllAccounts(string: String) {
        viewModelScope.launch {
            getAllAccountsUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    for (i in it) {
                        if (i.email == currentEmailLiveData.value) {
                            accountLiveData.value = i
                        }
                    }
                    println("$it $string")
                    isAnyAccountExist.value = (it.isNotEmpty())
                }
        }
    }

    fun initAccountAlreadyExist(email: String) {
        viewModelScope.launch {
            isAccountExistUseCase.saveInput(email)
            isAccountExistUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    isAccountAlreadyExist.value = it
                    clearAlreadyExistState()
                }
        }
    }

    private fun clearAlreadyExistState() {
        isAccountAlreadyExist.value = null
    }

    fun isAccountAlreadyExist() = isAccountAlreadyExist

    fun isAnyAccountExist() = isAnyAccountExist

    fun updateAccountPassword(account: Account, newEncodedPassword: String) {
        viewModelScope.launch {
            updateAccountPasswordUseCase.saveInput(account to newEncodedPassword)
            updateAccountPasswordUseCase.execute()
            accountLiveData.value = account.copy(encodedPassword = newEncodedPassword)
            currentEmailLiveData.value = account.email
            Log.i(
                "AccountViewModel",
                "Updating password: Account: $account, Value: ${accountLiveData.value}, Email: ${currentEmailLiveData.value}"
            )
        }
    }

    fun updateAccountData(account: Account) {
        viewModelScope.launch {
            updateAccountDataUseCase.saveInput(account)
            updateAccountDataUseCase.execute()
            accountLiveData.value = account
            Log.i(
                "AccountViewModel",
                "Updating accountLivedata value: Account: $account, Value: ${accountLiveData.value}"
            )
        }
    }

    fun setCurrentEmail(email: String) {
        viewModelScope.launch {
            insertCurrentEmailUseCase.saveInput(email)
            insertCurrentEmailUseCase.execute()
            currentEmailLiveData.value = email
        }
    }

    fun setAccount(account: Account) {
        viewModelScope.launch {
            insertAccountUseCase.saveInput(account)
            insertAccountUseCase.execute()
            accountLiveData.value = account
            Log.i(
                "AccountViewModel",
                "Setting to accountLivedata value: Account: $account, Value: ${accountLiveData.value}"
            )
        }
    }

    fun logout() {
        accountLiveData.value = null
        currentEmailLiveData.value = null
    }

    fun deleteAccount(email: String) {
        viewModelScope.launch {
            deleteAccountUseCase.saveInput(email)
            accountLiveData.value = null
            deleteAccountUseCase.execute()
        }
    }

    fun attemptToLogin(nickname: String, encodedPassword: String) {
        viewModelScope.launch {
            loginAttemptUseCase.saveInput(nickname, encodedPassword)
            loginAttemptUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    if (it != null) {
                        accountLiveData.value = it
                        isAccountExist.value = true
                    } else {
                        isAccountExist.value = false
                    }
                    isAccountExist.value = null
                }
        }
    }

    fun isAccountExist() = isAccountExist

    private fun initAccount() {
        viewModelScope.launch {
            val str = currentEmailLiveData.value ?: ""
            getAccountUseCase.saveInput(str)
            getAccountUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    accountLiveData.value = it
                    Log.i(
                        "AccountViewModel",
                        "Initializing accountLiveData: Account: $it, Value: ${accountLiveData.value}, Email is: $str"
                    )
                }
        }
    }

    fun getAccount() = accountLiveData
}
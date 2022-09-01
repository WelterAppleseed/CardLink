package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.usecases.CheckFirstTimeOnFragmentUseCase
import com.example.cardlinker.domain.usecases.CheckFirstTimeUsedReturnUseCase
import com.example.cardlinker.domain.usecases.CheckIsLoggedInUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppearanceViewModel @Inject constructor(
    private val checkFirstTimeUsedUseCase: CheckFirstTimeUsedReturnUseCase,
    private val checkFirstTimeOnFragmentUseCase: CheckFirstTimeOnFragmentUseCase,
    private val checkIsLoggedInUseCase: CheckIsLoggedInUseCase
) : BaseViewModel() {
    private val isFirstTimeUsed = MutableLiveData<Boolean>(null)
    private val isFirstTimeOnFragment = MutableLiveData<Boolean>(null)
    private val isLoggedIn = MutableLiveData<Boolean>(null)

    init {
        viewModelScope.launch {
            getFirstTimeUsedState()
        }
        isLoggedIn.value = checkIsLoggedInUseCase.execute()
    }
    fun updateLoginState(isLoggedIn: Boolean) {
        this.isLoggedIn.value = isLoggedIn
        checkIsLoggedInUseCase.updateState(isLoggedIn)
    }
    fun getIsLoggedIn() = isLoggedIn
    fun checkFirstTimeOnFragment(fragmentName: String) {
        checkFirstTimeOnFragmentUseCase.saveInput(fragmentName)
        isFirstTimeOnFragment.value = checkFirstTimeOnFragmentUseCase.execute()
    }
    fun updateFirstTimeOnFragmentState() {
        checkFirstTimeOnFragmentUseCase.updateState()
    }
    fun getIsFirstTimeOnFragment() = isFirstTimeOnFragment

    private suspend fun getFirstTimeUsedState() {
        isFirstTimeUsed.value = checkFirstTimeUsedUseCase.execute()
    }

    fun getIsFirstTimeUsed() = isFirstTimeUsed

    fun changeFirstTimeUsedState(isFirstTimeUsed: Boolean) {
        checkFirstTimeUsedUseCase.saveInput(isFirstTimeUsed)
        this.isFirstTimeUsed.value = isFirstTimeUsed
    }
}
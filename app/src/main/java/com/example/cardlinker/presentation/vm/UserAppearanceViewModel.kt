package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.usecases.CheckFirstTimeUsedReturnUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAppearanceViewModel @Inject constructor(
    private val checkFirstTimeUsedUseCase: CheckFirstTimeUsedReturnUseCase
): BaseViewModel() {
    private val isFirstTimeUsed = MutableLiveData<Boolean>(null)
    private val isLoggedIn = MutableLiveData<Boolean>(null)
    init {
        viewModelScope.launch {
            getFirstTimeUsedState()
        }
    }
    private suspend fun getFirstTimeUsedState() {
            isFirstTimeUsed.value = checkFirstTimeUsedUseCase.execute()
    }
    fun getIsFirstTimeUsed() = isFirstTimeUsed

    fun changeFirstTimeUsedState(isFirstTimeUsed: Boolean) {
        checkFirstTimeUsedUseCase.saveInput(isFirstTimeUsed)
        this.isFirstTimeUsed.value = isFirstTimeUsed
    }
}
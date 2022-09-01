package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.usecases.GetPatternUseCase
import com.example.cardlinker.domain.usecases.InsertPatternUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternViewModel @Inject constructor(
    private val insertPatternUseCase: InsertPatternUseCase,
    private val getPatternUseCase: GetPatternUseCase
): BaseViewModel() {
    private val patternLiveData = MutableLiveData<String>(null)

    init {
        initPattern()
    }
    private fun initPattern() {
       patternLiveData.value = getPatternUseCase.execute()
    }

    fun getPattern() = patternLiveData

    fun insertPattern(pattern: String) {
        viewModelScope.launch {
            insertPatternUseCase.saveInput(pattern)
            patternLiveData.value = pattern
            insertPatternUseCase.execute()
        }
    }
}
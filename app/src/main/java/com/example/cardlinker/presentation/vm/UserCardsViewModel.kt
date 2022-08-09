package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.usecases.GetCardsUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase
) : BaseViewModel() {
    private val cardsLiveData = MutableLiveData<List<Card>>(null)
    private val code = MutableLiveData<String>(null)

    init {
        initializeCards()
    }

    private fun initializeCards() {
        viewModelScope.launch {
            getCardsUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    cardsLiveData.value = it
                }
        }

    }

    fun saveCode(code: String) {
        this.code.value = code
    }

    fun getCode() = code
    fun saveCard(card: Card) {
        viewModelScope.launch {
            getCardsUseCase.saveCard(card)
            code.value = ""
        }
    }

    fun getCards() = cardsLiveData
}
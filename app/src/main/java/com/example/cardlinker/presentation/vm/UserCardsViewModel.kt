package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.usecases.DeleteCardReturnUseCase
import com.example.cardlinker.domain.usecases.GetCardsReturnUseCase
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsReturnUseCase,
    private val deleteCardsUseCase: DeleteCardReturnUseCase
) : BaseViewModel() {
    private val cardsLiveData = MutableLiveData<ArrayList<Card>>(null)
    private val codeLiveData = MutableLiveData<Code?>(null)

    init {
        initializeCards()
    }

    private fun initializeCards() {
        viewModelScope.launch {
            getCardsUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    cardsLiveData.value = ArrayList(it)
                }
        }

    }

    fun saveCode(code: Code) {
        this.codeLiveData.value = code
    }

    fun getCode() = codeLiveData

    fun saveCard(card: Card) {
        viewModelScope.launch {
            getCardsUseCase.saveCard(card)
            cardsLiveData.value?.add(card)
            codeLiveData.value = null
        }
    }

    fun deleteCard(cardNumber: String) {
        for (i in cardsLiveData.value!!) if (i.number== cardNumber) {
            cardsLiveData.value?.remove(i)
        }
        viewModelScope.launch {
            deleteCardsUseCase.saveInput(cardNumber)
            deleteCardsUseCase.execute()
        }
    }
    fun deleteCode() = run {codeLiveData.value = null}
    fun getCards() = cardsLiveData
}
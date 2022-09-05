package com.example.cardlinker.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.domain.models.Code
import com.example.cardlinker.domain.usecases.*
import com.example.cardlinker.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCardsViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsReturnUseCase,
    private val deleteCardUseCase: DeleteCardReturnUseCase,
    private val getNotLinkedCardsUseCase: GetNotLinkedCardsUseCase,
    private val getLinkedCardsUseCase: GetLinkedCardsUseCase,
    private val updateCardAccountHashcodeUseCase: UpdateCardAccountHashcodeUseCase,
    private val deleteCardsUseCase: DeleteCardsUseCase
) : BaseViewModel() {
    private val cardsLiveData = MutableLiveData<MutableList<Card>>(null)
    private val codeLiveData = MutableLiveData<Code?>(null)
    private val notLinkedCards = MutableLiveData<ArrayList<Card>>(null)
    private val notLinkedCardCodes = MutableLiveData<List<Code>>(null)
    private val linkedCards = MutableLiveData<MutableList<Card>>(null)
    init {
        initializeCards()
        initNotLinkedCards()
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

    fun saveCard(card: Card, isLinked: Boolean) {
        viewModelScope.launch {
            getCardsUseCase.saveCard(card)
            cardsLiveData.value?.add(card)
            if (isLinked) linkedCards.value?.add(card) else notLinkedCards.value?.add(card)
            codeLiveData.value = null
        }
    }

    private fun initNotLinkedCards() {
        viewModelScope.launch {
            getNotLinkedCardsUseCase.execute()
                .distinctUntilChanged()
                .collect { list ->
                    notLinkedCards.value = ArrayList(list)
                    val codes = mutableListOf<Code>()
                    list.forEach {
                        codes.add(it.code)
                    }
                    notLinkedCardCodes.value = codes
                }
        }
    }
    fun updateCardsWithAccountHashCode(newAccountHashCode: Int, oldAccountHashCode: Int) {
        viewModelScope.launch {
            updateCardAccountHashcodeUseCase.saveInput(newAccountHashCode, oldAccountHashCode)
            updateCardAccountHashcodeUseCase.execute()
        }
    }
    fun getNotLinkedCards() = notLinkedCards
    fun getNotLinkedCardCodes() = notLinkedCardCodes
    fun initLinkedCards(accountHashCode: Int) {
        viewModelScope.launch {
            getLinkedCardsUseCase.saveInput(accountHashCode)
            getLinkedCardsUseCase.execute()
                .distinctUntilChanged()
                .collect {
                    linkedCards.value = ArrayList(it)
                }
        }
    }
    fun getLinkedCards() = linkedCards
    fun deleteCard(cardNumber: String, isLinked: Boolean) {
        for (i in cardsLiveData.value!!) if (i.number == cardNumber) {
            cardsLiveData.value?.remove(i)
            if (isLinked) linkedCards.value?.remove(i) else notLinkedCards.value?.remove(i)
            break
        }
        viewModelScope.launch {
            deleteCardUseCase.saveInput(cardNumber)
            deleteCardUseCase.execute()
        }
    }
    fun deleteCards(accountHashCode: Int) {
        viewModelScope.launch {
            deleteCardsUseCase.saveInput(accountHashCode)
            deleteCardsUseCase.execute()
        }
    }

    fun deleteCode() = run { codeLiveData.value = null }
    fun getCards() = cardsLiveData
}
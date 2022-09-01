package com.example.cardlinker.presentation.fragments.menu.cards_management

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.databinding.FragmentCardsManagementBinding
import com.example.cardlinker.domain.models.Card
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.disable
import com.example.cardlinker.util.enable
import com.example.cardlinker.util.objects.Constants
import com.example.cardlinker.util.withAnimatedScrolling

class CardsManagementFragment :
    BaseFragment<FragmentCardsManagementBinding>(FragmentCardsManagementBinding::inflate),
    OnSmallCardDeleteIconClickedListener {
    private val userCardsViewModel: UserCardsViewModel by activityViewModels()
    private val accountViewModel: AccountViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initToolbar()
    }

    private fun initToolbar() {
        binding.apply {
            cardsManagementToolbar.setNavigationOnClickListener {
                navigationViewModel.goToMenuFragment()
            }
        }
    }

    private fun initRecycler() {
        binding.apply {
            userAppearanceViewModel.getIsLoggedIn().observe(viewLifecycleOwner) { isLoggedIn ->
                if (isLoggedIn) {
                    accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
                        if (account != null) {
                            userCardsViewModel.initLinkedCards(account.hashCode())
                            userCardsViewModel.getLinkedCards()
                                .observe(viewLifecycleOwner) { cards ->
                                    if (cards.isNotEmpty()) {
                                        smallCardsLayout.enable()
                                        noCardsLayout.root.visibility = View.GONE
                                        smallCardsLayout.withAnimatedScrolling()
                                    } else {
                                        noCardsLayout.root.visibility = View.VISIBLE
                                        smallCardsLayout.disable()
                                    }
                                    val adapter =
                                        CardsManagementAdapter(cards, this@CardsManagementFragment)
                                    val layoutManager = LinearLayoutManager(context)
                                    binding.apply {
                                        smallCardsRecycler.adapter = adapter
                                        smallCardsRecycler.layoutManager = layoutManager
                                    }
                                }
                        }
                    }
                } else {
                    userCardsViewModel.getNotLinkedCards().observe(viewLifecycleOwner) { cards ->
                        if (cards.isNotEmpty()) {
                            smallCardsLayout.enable()
                            noCardsLayout.root.visibility = View.GONE
                            smallCardsLayout.withAnimatedScrolling()
                        } else {
                            noCardsLayout.root.visibility = View.VISIBLE
                            smallCardsLayout.disable()
                        }
                        val adapter = CardsManagementAdapter(cards, this@CardsManagementFragment)
                        val layoutManager = LinearLayoutManager(context)
                        binding.apply {
                            smallCardsRecycler.adapter = adapter
                            smallCardsRecycler.layoutManager = layoutManager
                        }
                    }
                }
            }
        }
    }

    override fun onDeleteClicked(card: Card, accountHashCode: Int) {
        userCardsViewModel.deleteCard(
            card.number.toString(),
            (accountHashCode != Constants.ACCOUNT_IS_NULL)
        )
    }
}
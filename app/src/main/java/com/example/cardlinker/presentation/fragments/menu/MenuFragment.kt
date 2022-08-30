package com.example.cardlinker.presentation.fragments.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.databinding.FragmentMenuBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.util.enums.CardMenuItem

class MenuFragment: BaseFragment<FragmentMenuBinding>(FragmentMenuBinding::inflate), OnMenuItemClickedListener {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }
    private fun initRecycler() {
        binding.apply {
            menuItemsRec.adapter = MenuFragmentAdapter(this@MenuFragment)
            menuItemsRec.layoutManager = LinearLayoutManager(context)
        }
    }
    override fun onMenuItemClicked(cardMenuItem: CardMenuItem) {
        when (cardMenuItem) {
            CardMenuItem.PROFILE -> navigationViewModel.goToProfileFragment()
            CardMenuItem.CARDS_MANAGEMENT -> navigationViewModel.goToCardsManagementFragment()
            CardMenuItem.SETTINGS -> navigationViewModel.goToSettingsFragment()
        }
    }
}
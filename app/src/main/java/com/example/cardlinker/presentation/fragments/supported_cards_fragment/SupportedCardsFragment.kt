package com.example.cardlinker.presentation.fragments.supported_cards_fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.databinding.FragmentSupportedCardsBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.RecommendationViewModel

class SupportedCardsFragment: BaseFragment<FragmentSupportedCardsBinding>(FragmentSupportedCardsBinding::inflate) {
    private val recommendationViewModel: RecommendationViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initToolbar()

    }
    private fun initRecycler() {
        recommendationViewModel.getRecommendations().observe(viewLifecycleOwner) {
            binding.apply {
                supportedCardsRecycler.adapter = SupportedCardsAdapter(it)
                supportedCardsRecycler.layoutManager = LinearLayoutManager(context)
            }
        }
    }
    private fun initToolbar() {
        binding.apply {
            supCardToolbar.setNavigationOnClickListener {
                navigationViewModel.goToUserCardsFragment()
            }
        }
    }
}
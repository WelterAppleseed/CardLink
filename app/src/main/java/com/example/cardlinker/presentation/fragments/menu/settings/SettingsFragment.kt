package com.example.cardlinker.presentation.fragments.menu.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardlinker.databinding.FragmentSettingsBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.util.enums.SettingsItem

class SettingsFragment: BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate), OnSettingItemClickedListener {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initToolbar()
    }
    private fun initToolbar() {
        binding.apply {
            settingsToolbar.setNavigationOnClickListener {
                navigationViewModel.goToMenuFragment()
            }
        }
    }
    private fun initRecycler() {
        binding.apply {
            userAppearanceViewModel.getIsLoggedIn().observe(viewLifecycleOwner) {
                val adapter = SettingsAdapter(this@SettingsFragment, it)
                val layoutManager = LinearLayoutManager(context)
                settingsRecycler.adapter = adapter
                settingsRecycler.layoutManager = layoutManager
            }
        }
    }
    override fun onSettingClicked(settingsItem: SettingsItem) {
        when (settingsItem) {
            SettingsItem.PASSWORD -> navigationViewModel.goToOldPasswordChangeFragment()
            else -> {}
        }
    }
}
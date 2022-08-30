package com.example.cardlinker.presentation.fragments.menu.settings

import com.example.cardlinker.util.enums.SettingsItem

interface OnSettingItemClickedListener {
    fun onSettingClicked(settingsItem: SettingsItem)
}
package com.example.cardlinker.presentation.fragments.menu.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardlinker.databinding.ItemSettingBinding
import com.example.cardlinker.util.enums.SettingsItem
import com.example.cardlinker.util.objects.Constants

class SettingsAdapter(private val onSettingItemClickedListener: OnSettingItemClickedListener, private val isLoggedIn: Boolean) : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {
    companion object {
        private val SETTING_ITEMS = SettingsItem.values()
    }
    inner class ViewHolder(private val binding: ItemSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(settingsItem: SettingsItem) {
            binding.apply {
                settingTv.text = settingsItem.text
                if (!isLoggedIn && ((settingsItem == SettingsItem.PASSWORD || settingsItem == SettingsItem.DELETE_ACCOUNT))) {
                    root.alpha = Constants.ALPHA_HALF_VISIBILITY
                } else {
                    root.setOnClickListener {
                        onSettingItemClickedListener.onSettingClicked(settingsItem)
                    }
                }
                if (SETTING_ITEMS.indexOf(settingsItem) == SETTING_ITEMS.size-1) fullSettingDivider.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(SETTING_ITEMS[position])
    }

    override fun getItemCount(): Int {
        return SETTING_ITEMS.size
    }
}
package com.example.cardlinker.util.enums

import com.example.cardlinker.R

enum class CardMenuItem(val icon: Int, val text: String) {
    PROFILE(R.drawable.profile_dr, "Profile"),
    CARDS_MANAGEMENT(R.drawable.cards_management_dr, "Cards Management"),
    SECURITY(R.drawable.security_dr, "Security"),
    SUPPORT(R.drawable.support_dr, "Support"),
    ABOUT_APP(R.drawable.about_app_dr, "About App"),
}
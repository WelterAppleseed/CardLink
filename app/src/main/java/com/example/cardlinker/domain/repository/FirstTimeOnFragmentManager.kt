package com.example.cardlinker.domain.repository

interface FirstTimeOnFragmentManager {
    fun getFirstTimeOnFragment(fragmentName: String): Boolean
    fun updateFirstTimeOnFragment(fragmentName: String)
}
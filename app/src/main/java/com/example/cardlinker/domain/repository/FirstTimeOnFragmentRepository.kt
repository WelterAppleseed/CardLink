package com.example.cardlinker.domain.repository

interface FirstTimeOnFragmentRepository {
    fun getFirstTimeOnFragment(fragmentName: String): Boolean
    fun updateFirstTimeOnFragment(fragmentName: String)
}
package com.example.cardlinker.presentation.fragments.menu.settings.password_change.new_password

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentNewPasswordChangeBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.security.AESCrypt
import com.example.cardlinker.util.setupUI

class NewPasswordChangeFragment :
    BaseFragment<FragmentNewPasswordChangeBinding>(FragmentNewPasswordChangeBinding::inflate) {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val accountViewModel: AccountViewModel by activityViewModels()
    private val userCardsViewModel: UserCardsViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChangePasswordEditText()
        requireActivity().setupUI(binding.root)
        initContinueButtons()
        initToolbar()
    }
    private fun initToolbar() {
        binding.apply {
            newChangePasswordToolbar.setNavigationOnClickListener {
                navigationViewModel.goToOldPasswordChangeFragment()
            }
        }
    }
    private fun initContinueButtons() {
        accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
            if (account != null) {
                binding.apply {
                    newChangePasswordContinueB.setOnClickListener {
                        if (newChangePasswordEt.text.length >= 6) {
                            val password = AESCrypt.encrypt(newChangePasswordEt.text.toString())
                            accountViewModel.updateAccountPassword(account, password)
                            userCardsViewModel.updateCardsWithAccountHashCode(
                                account.copy(
                                    encodedPassword = password
                                ).hashCode(), account.hashCode()
                            )
                            navigationViewModel.goToMenuFragment()
                        } else {
                            newChangePasswordEt.setBackgroundResource(R.drawable.change_password_wrong_password_b)
                            smallPasswordTv.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }
    private fun initChangePasswordEditText() {
        binding.apply {
            newChangePasswordEt.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) newChangePasswordEt.setBackgroundResource(R.drawable.change_password_focused_b) else newChangePasswordEt.setBackgroundResource(
                    R.drawable.change_password_unfocused_b
                )
            }
            newChangePasswordEt.doOnTextChanged { text, start, before, count ->
                if (newChangePasswordEt.background == ResourcesCompat.getDrawable(resources, R.drawable.change_password_wrong_password_b, null)) {
                    newChangePasswordEt.setBackgroundResource(R.drawable.change_password_focused_b)
                    smallPasswordTv.visibility = View.GONE
                }
            }
        }
    }

    override var bottomNavigationViewVisibility: Int = View.GONE
    override var topBarColor: Int = R.color.theme_background_color
    override var windowSoftInput: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
}
package com.example.cardlinker.presentation.fragments.menu.settings.password_change.old_password

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentOldPasswordChangeBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.util.security.AESCrypt
import com.example.cardlinker.util.setupUI

class OldPasswordChangeFragment :
    BaseFragment<FragmentOldPasswordChangeBinding>(FragmentOldPasswordChangeBinding::inflate) {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val accountViewModel: AccountViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContinueButtons()
        requireActivity().setupUI(binding.root)
        initChangePasswordEditText()
        initToolbars()
    }
    private fun initToolbars() {
        binding.apply {
            oldChangePasswordToolbar.setNavigationOnClickListener {
                navigationViewModel.goToMenuFragment()
            }
        }
    }
    private fun initContinueButtons() {
        accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
            binding.apply {
                if (account != null) {
                    changePasswordContinueB.setOnClickListener {
                        if (changePasswordEt.text.isNotEmpty()) {
                            accountViewModel.attemptToLogin(
                                account.nickname,
                                AESCrypt.encrypt(changePasswordEt.text.toString())
                            )
                            accountViewModel.isAccountExist()
                                .observe(viewLifecycleOwner) { isExist ->
                                    if (isExist != null) {
                                        if (isExist == true) {
                                            enterPTv.text = context?.getString(R.string.come_up)
                                            changePasswordEt.hint =
                                                context?.getString(R.string.new_p)
                                            toChangeItTv.text =
                                                context?.getString(R.string.at_least)
                                            navigationViewModel.goToNewPasswordChangeFragment()
                                        } else {
                                            changePasswordEt.setBackgroundResource(R.drawable.change_password_wrong_password_b)
                                            wrongPasswordTv.visibility = View.VISIBLE
                                        }
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun initChangePasswordEditText() {
        binding.apply {
            changePasswordEt.setOnFocusChangeListener { _, isFocused ->
                if (isFocused) changePasswordEt.setBackgroundResource(R.drawable.change_password_focused_b) else changePasswordEt.setBackgroundResource(
                    R.drawable.change_password_unfocused_b
                )
            }
            changePasswordEt.doOnTextChanged { text, start, before, count ->
                if (wrongPasswordTv.visibility == View.VISIBLE) {
                    changePasswordEt.setBackgroundResource(R.drawable.change_password_focused_b)
                    wrongPasswordTv.visibility = View.GONE
                }
            }
        }
    }
    override var bottomNavigationViewVisibility: Int = View.GONE
    override var topBarColor: Int = R.color.theme_background_color
    override var windowSoftInput: Int = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
}
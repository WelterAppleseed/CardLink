package com.example.cardlinker.presentation.fragments.menu.profile

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentProfileBinding
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.disable
import com.example.cardlinker.util.enable
import com.example.cardlinker.util.isNormal
import com.example.cardlinker.util.isWrong
import com.example.cardlinker.util.objects.Constants
import com.example.cardlinker.util.security.AESCrypt

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    private val accountViewModel: AccountViewModel by activityViewModels()
    private val userCardsViewModel: UserCardsViewModel by activityViewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by activityViewModels()
    override var bottomNavigationViewVisibility = View.GONE

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProfileData()
        initToolbar()
        initLoginOrRegisterLayout(false)
        initEditTexts()
    }

    private fun updateToolbar(loggedIn: Boolean) {
        binding.apply {
            if (loggedIn) {
                profileToolbar.inflateMenu(R.menu.log_out_menu)
                profileToolbar.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.action_log_out -> {
                            accountViewModel.logout()
                            userAppearanceViewModel.updateLoginState(false)
                            navigationViewModel.goToMenuFragment()
                            return@setOnMenuItemClickListener true
                        }
                        else -> {
                            return@setOnMenuItemClickListener false
                        }
                    }
                }
            } else {
                profileToolbar.menu.clear()
            }
        }
    }

    private fun initLoginOrRegisterLayout(isAnyAccountExist: Boolean) {
        binding.apply {
            profileDataLayout.doesHaveAccountTv.setOnClickListener {
                if (profileDataLayout.doesHaveAccountTv.text == context?.getString(R.string.already_have_account_quest)) {
                    profileDataLayout.mailEt.visibility = View.GONE
                    profileDataLayout.nicknameEt.hint = context?.getString(R.string.nick_or_email)
                    profileDataLayout.doesHaveAccountTv.text =
                        if (isAnyAccountExist) context?.getString(R.string.need_another) else
                            context?.getString(R.string.doesnt_have_account_quest)
                    saveButton.text = context?.getString(R.string.login)
                } else {
                    profileDataLayout.mailEt.visibility = View.VISIBLE
                    profileDataLayout.nicknameEt.hint = context?.getString(R.string.name)
                    profileDataLayout.doesHaveAccountTv.text =
                        context?.getString(R.string.already_have_account_quest)
                    saveButton.text = context?.getString(R.string.register)
                }
            }
        }
    }

    private fun initEditTexts() {
        binding.profileDataLayout.apply {
            nicknameEt.doOnTextChanged { _, _, _, _ ->
                nicknameEt.isNormal()
            }
            passwordEt.doOnTextChanged { _, _, _, _ ->
                passwordEt.isNormal()
            }
            mailEt.doOnTextChanged { _, _, _, _ ->
                mailEt.isNormal()
            }
        }
    }

    private fun initProfileData() {
        userAppearanceViewModel.getIsLoggedIn().observe(viewLifecycleOwner) { isLoggedIn ->
            accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
                if (isLoggedIn) {
                    binding.apply {
                        if (account != null) {
                            profileDataLayout.doesHaveAccountTv.visibility = View.GONE
                            profileDataLayout.passwordEt.disable()
                            profileDataLayout.passwordEt.alpha = Constants.ALPHA_HALF_VISIBILITY
                            profileDataLayout.nicknameEt.setText(account.nickname)
                            profileDataLayout.mailEt.setText(account.email)
                            profileDataLayout.passwordEt.setText(AESCrypt.decrypt(account.encodedPassword))
                            saveButton.setText(R.string.save)
                            updateToolbar(true)
                        }
                    }
                } else {
                    accountViewModel.isAnyAccountExist().observe(viewLifecycleOwner) {
                        if (it != true) {
                            binding.apply {
                                profileDataLayout.passwordEt.enable()
                                profileDataLayout.passwordEt.alpha = Constants.ALPHA_FULL_VISIBILITY
                                profileDataLayout.nicknameEt.text.clear()
                                profileDataLayout.mailEt.text.clear()
                                profileDataLayout.passwordEt.text.clear()
                                profileDataLayout.doesHaveAccountTv.visibility = View.VISIBLE
                                saveButton.setText(R.string.register)
                                updateToolbar(false)
                            }
                        } else {
                            binding.apply {
                                initLoginOrRegisterLayout(true)
                                profileDataLayout.doesHaveAccountTv.performClick()
                            }
                        }
                    }
                }
                binding.apply {
                    if (profileDataLayout.doesHaveAccountTv.text == context?.getString(R.string.already_have_account_quest)) {
                        saveButton.setOnClickListener {
                            if (profileDataLayout.nicknameEt.text.isNotEmpty() && profileDataLayout.mailEt.text.isNotEmpty() && profileDataLayout.passwordEt.text.length >= 6) {
                                if (account != null) {
                                    updateAccount(account)
                                } else {
                                    insertAccount(account)
                                }
                            } else {
                                binding.profileDataLayout.apply {
                                    for (i in listOf(passwordEt, mailEt, nicknameEt)) {
                                        if (i.text.isEmpty() || (i == passwordEt && i.length() < 6)) i.isWrong()
                                    }
                                }
                            }
                        }
                    } else {
                        accountViewModel.isAccountExist()
                            .observe(viewLifecycleOwner) { accountExist ->
                                saveButton.setOnClickListener {
                                    if (profileDataLayout.nicknameEt.text.isNotEmpty() && profileDataLayout.passwordEt.text.isNotEmpty()) {
                                        accountViewModel.attemptToLogin(
                                            profileDataLayout.nicknameEt.text.toString(),
                                            AESCrypt.encrypt(profileDataLayout.passwordEt.text.toString())
                                        )
                                        if (accountExist) {
                                            userAppearanceViewModel.updateLoginState(true)
                                            navigationViewModel.goToMenuFragment()
                                        } else {
                                            profileDataLayout.passwordEt.isWrong()
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private fun updateAccount(account: Account) {
        binding.apply {
            accountViewModel.initAccountAlreadyExist(profileDataLayout.mailEt.text.toString())
            accountViewModel.isAccountAlreadyExist().observe(viewLifecycleOwner) { isExist ->
                if (isExist != null) {
                    if (isExist == false || account.email == profileDataLayout.mailEt.text.toString()) {
                        if (profileDataLayout.doesHaveAccountTv.visibility == View.GONE) {
                            val updatedAccount = account.copy(
                                nickname = profileDataLayout.nicknameEt.text.toString(),
                                email = profileDataLayout.mailEt.text.toString()
                            )
                            accountViewModel.updateAccountData(
                                updatedAccount
                            )
                            userCardsViewModel.updateCardsWithAccountHashCode(updatedAccount.hashCode(), account.hashCode())
                            navigationViewModel.goToMenuFragment()
                        }
                    } else {
                        profileDataLayout.mailEt.isWrong()
                    }
                }
            }
        }
    }

    private fun insertAccount(account: Account?) {
        binding.apply {
            userCardsViewModel.getNotLinkedCardCodes()
                .observe(viewLifecycleOwner) { codes ->
                    val encodedPassword = AESCrypt.encrypt(profileDataLayout.passwordEt.text.toString())
                    val registeredAccount = Account(
                        nickname = profileDataLayout.nicknameEt.text.toString(),
                        email = profileDataLayout.mailEt.text.toString(),
                        encodedPassword = encodedPassword,
                        cardCodes = codes
                    )
                    accountViewModel.setCurrentEmail(profileDataLayout.mailEt.text.toString())
                    accountViewModel.setAccount(registeredAccount)
                    userCardsViewModel.updateCardsWithAccountHashCode(
                        registeredAccount.hashCode(),
                        Constants.ACCOUNT_IS_NULL
                    )
                }
            userAppearanceViewModel.updateLoginState(true)
            navigationViewModel.goToMenuFragment()
        }
    }

    private fun initToolbar() {
        binding.apply {
            profileToolbar.setNavigationOnClickListener {
                navigationViewModel.goToMenuFragment()
            }
        }
    }
}
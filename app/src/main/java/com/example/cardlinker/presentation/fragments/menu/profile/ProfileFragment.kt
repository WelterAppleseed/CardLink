package com.example.cardlinker.presentation.fragments.menu.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cardlinker.R
import com.example.cardlinker.data.local.db.CardLinkDatabase
import com.example.cardlinker.databinding.FragmentProfileBinding
import com.example.cardlinker.domain.models.Account
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.example.cardlinker.presentation.vm.UserCardsViewModel
import com.example.cardlinker.util.disable
import com.example.cardlinker.util.enable
import com.example.cardlinker.util.objects.Constants
import com.example.cardlinker.util.security.AESCrypt
import kotlin.concurrent.thread

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
        initLoginOrRegisterLayout()
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

    private fun initLoginOrRegisterLayout() {
        binding.apply {
            profileDataLayout.doesHaveAccountTv.setOnClickListener {
                if (profileDataLayout.doesHaveAccountTv.text == context?.getString(R.string.already_have_account_quest)) {
                    profileDataLayout.mailEt.visibility = View.GONE
                    profileDataLayout.nicknameEt.hint = context?.getString(R.string.nick_or_email)
                    profileDataLayout.doesHaveAccountTv.text =
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

    private fun initProfileData() {
        accountViewModel.getAccount().observe(viewLifecycleOwner) { account ->
            userAppearanceViewModel.getIsLoggedIn().observe(viewLifecycleOwner) { isLoggedIn ->
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
                }
            }
            binding.apply {
                saveButton.setOnClickListener {
                    if (profileDataLayout.doesHaveAccountTv.text == context?.getString(R.string.already_have_account_quest)) {
                        if (profileDataLayout.nicknameEt.text.isNotEmpty() && profileDataLayout.mailEt.text.isNotEmpty() && profileDataLayout.passwordEt.text.isNotEmpty()) {
                            userCardsViewModel.getNotLinkedCardCodes()
                                .observe(viewLifecycleOwner) { codes ->
                                    val encodedPassword =
                                        account?.encodedPassword ?: AESCrypt.encrypt(
                                            profileDataLayout.passwordEt.text.toString()
                                        )
                                    accountViewModel.setPassword(encodedPassword)
                                    val registeredAccount = Account(
                                        id = account?.id ?: (0),
                                        nickname = profileDataLayout.nicknameEt.text.toString(),
                                        email = profileDataLayout.mailEt.text.toString(),
                                        encodedPassword = encodedPassword,
                                        cardCodes = account?.cardCodes ?: codes
                                    )
                                    accountViewModel.setAccount(registeredAccount)
                                    userCardsViewModel.updateNotLinkedCardsWithAccountHashCode(
                                        registeredAccount.hashCode()
                                    )
                                }
                        }
                        userAppearanceViewModel.updateLoginState(true)
                        navigationViewModel.goToMenuFragment()
                    } else {
                        if (profileDataLayout.nicknameEt.text.isNotEmpty() && profileDataLayout.passwordEt.text.isNotEmpty()) {
                            accountViewModel.attemptToLogin(
                                profileDataLayout.nicknameEt.text.toString(),
                                AESCrypt.encrypt(profileDataLayout.passwordEt.text.toString())
                            )
                            accountViewModel.isAccountExist().observe(viewLifecycleOwner) {
                                if (it == true) {
                                    userAppearanceViewModel.updateLoginState(true)
                                    navigationViewModel.goToMenuFragment()
                                }
                            }
                        }
                    }
                }
            }
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
package com.example.cardlinker.presentation.fragments.menu.settings.pattern_lock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentPatternLockBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.PatternViewModel
import me.zhanghai.android.patternlock.PatternUtils
import me.zhanghai.android.patternlock.PatternView

class PatternLockFragment: BaseFragment<FragmentPatternLockBinding>(FragmentPatternLockBinding::inflate) {
    private val patternViewModel: PatternViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPattern()
        initRestorePatternButton()
        initToolbar()
    }
    private fun initRestorePatternButton() {
        binding.apply {
            patternRestoreB.setOnClickListener {
                inputProtectionTv.text = context?.getString(R.string.pattern_login)
                inputProtectionTvSmall.text = context?.getString(R.string.pattern_login_small)
                inputProtectionTvSmall.setTextColor(resources.getColor(R.color.grey5, null))
                patternView.clearPattern()
                patternRestoreB.visibility = View.GONE
            }
        }
    }
    private fun initPattern() {
        patternViewModel.getPattern().observe(viewLifecycleOwner) { storedPattern ->
            if (storedPattern.isNotEmpty()) {
                binding.apply {
                    var cachePattern = ""
                    inputProtectionTv.text = context?.getString(R.string.inp_old_pattern)
                    inputProtectionTvSmall.text = context?.getString(R.string.inp_old_pattern_small)
                    patternView.setOnPatternListener(object: PatternView.OnPatternListener {
                        override fun onPatternStart() {
                        }

                        override fun onPatternCleared() {
                        }

                        override fun onPatternCellAdded(pattern: MutableList<PatternView.Cell>?) {
                        }

                        override fun onPatternDetected(pattern: MutableList<PatternView.Cell>?) {
                            if (inputProtectionTv.text == context?.getString(R.string.inp_old_pattern)) {
                                if (storedPattern == PatternUtils.patternToSha1String(pattern)) {
                                    inputProtectionTvSmall.setTextColor(resources.getColor(R.color.grey5, null))
                                    inputProtectionTv.text =
                                        context?.getString(R.string.inp_old_pattern_new)
                                    inputProtectionTvSmall.text =
                                        context?.getString(R.string.inp_old_pattern_new_small)
                                    patternView.clearPattern()
                                    patternRestoreB.visibility = View.VISIBLE
                                } else {
                                    inputProtectionTvSmall.text = context?.getString(R.string.pattern_is_wrong)
                                    inputProtectionTvSmall.setTextColor(resources.getColor(R.color.primaryRedColor, null))
                                    patternView.displayMode = PatternView.DisplayMode.Wrong
                                }
                            } else {
                                if (inputProtectionTv.text == context?.getString(R.string.inp_old_pattern_new) || inputProtectionTv.text == context?.getString(R.string.pattern_login)) {
                                    inputProtectionTv.text = context?.getString(R.string.inp_new_pattern_new)
                                    cachePattern = PatternUtils.patternToSha1String(pattern)
                                    inputProtectionTvSmall.text = context?.getString(R.string.inp_new_pattern_new_small)
                                    patternRestoreB.visibility = View.VISIBLE
                                    patternView.clearPattern()
                                } else {
                                    if (PatternUtils.patternToSha1String(pattern) == cachePattern) {
                                        patternViewModel.insertPattern(cachePattern)
                                        navigationViewModel.goToSettingsFragment()
                                    } else {
                                        inputProtectionTvSmall.text = context?.getString(R.string.patterns_are_not_equal)
                                        inputProtectionTvSmall.setTextColor(resources.getColor(R.color.primaryRedColor, null))
                                        patternView.displayMode = PatternView.DisplayMode.Wrong
                                    }
                                }
                            }
                        }

                    })
                }
            } else {
                var cachePattern = ""
                binding.apply {
                    inputProtectionTv.text = context?.getString(R.string.pattern_login)
                    inputProtectionTvSmall.text = context?.getString(R.string.pattern_login_small)
                    patternView.setOnPatternListener(object: PatternView.OnPatternListener {
                        override fun onPatternStart() {
                        }

                        override fun onPatternCleared() {
                        }

                        override fun onPatternCellAdded(pattern: MutableList<PatternView.Cell>?) {
                        }

                        override fun onPatternDetected(pattern: MutableList<PatternView.Cell>?) {
                            if (inputProtectionTv.text == context?.getString(R.string.pattern_login)) {
                                inputProtectionTv.text = context?.getString(R.string.inp_new_pattern_new)
                                inputProtectionTvSmall.text = context?.getString(R.string.inp_new_pattern_new_small)
                                cachePattern = PatternUtils.patternToSha1String(pattern)
                                patternView.clearPattern()
                                patternRestoreB.visibility = View.VISIBLE
                            } else {
                                if (PatternUtils.patternToSha1String(pattern) == cachePattern) {
                                    patternViewModel.insertPattern(cachePattern)
                                    navigationViewModel.goToSettingsFragment()
                                } else {
                                    inputProtectionTvSmall.text = context?.getString(R.string.patterns_are_not_equal)
                                    inputProtectionTvSmall.setTextColor(resources.getColor(R.color.primaryRedColor, null))
                                    patternView.displayMode = PatternView.DisplayMode.Wrong
                                }
                            }

                        }

                    })
                }
            }
        }
    }
    private fun initToolbar() {
        binding.patternLockToolbar.apply {
            setNavigationOnClickListener {
                navigationViewModel.goToSettingsFragment()
            }
        }
    }
    override var bottomNavigationViewVisibility: Int = View.GONE
}
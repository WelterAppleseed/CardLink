package com.example.cardlinker.presentation.fragments.enter_pattern

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.example.cardlinker.R
import com.example.cardlinker.databinding.FragmentEnterPatternBinding
import com.example.cardlinker.presentation.base.BaseFragment
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.PatternViewModel
import me.zhanghai.android.patternlock.PatternUtils
import me.zhanghai.android.patternlock.PatternView

class EnterPatternFragment: BaseFragment<FragmentEnterPatternBinding>(FragmentEnterPatternBinding::inflate) {
    private val patternViewModel: PatternViewModel by activityViewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEnterPattern()
    }
    private fun initEnterPattern() {
        patternViewModel.getPattern().observe(viewLifecycleOwner) { storedPattern ->
            binding.apply {
                if (storedPattern.isNotEmpty()) {
                    enterPattern.setOnPatternListener(object: PatternView.OnPatternListener {
                        override fun onPatternStart() {
                        }

                        override fun onPatternCleared() {
                        }

                        override fun onPatternCellAdded(pattern: MutableList<PatternView.Cell>?) {
                        }

                        override fun onPatternDetected(pattern: MutableList<PatternView.Cell>?) {
                           if (PatternUtils.patternToSha1String(pattern) == storedPattern) {
                               navigationViewModel.goToUserCardsFragment()
                           } else {
                               enterPattern.displayMode = PatternView.DisplayMode.Wrong
                           }
                        }

                    })
                }
            }
        }
    }

    override var bottomNavigationViewVisibility: Int = View.GONE
    override var topBarColor: Int = R.color.theme_background_color
}
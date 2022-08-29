package com.example.cardlinker.presentation.activities

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cardlinker.R
import com.example.cardlinker.presentation.vm.AccountViewModel
import com.example.cardlinker.presentation.vm.NavigationViewModel
import com.example.cardlinker.presentation.vm.UserAppearanceViewModel
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val navigationViewModel: NavigationViewModel by viewModels()
    private val userAppearanceViewModel: UserAppearanceViewModel by viewModels()
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private val navigator = AppNavigator(this, R.id.main_fragment_container)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationViewInitialization()
        userAppearanceViewModel.getIsFirstTimeUsed().observe(this) { firstTime ->
            if (firstTime) {
                navigationViewModel.goToEnterBannersFragment()
            } else {
                navigationViewModel.goToUserCardsFragment()
            }
        }
    }
    private fun bottomNavigationViewInitialization() {
        this.bottom_nav_view.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_cards -> {
                    navigationViewModel.goToUserCardsFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.action_menu -> {
                    navigationViewModel.goToMenuFragment()
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }
    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
    fun setBottomNavigationBarVisibility(visibility: Int) {
      this.bottom_nav_view.visibility = visibility
    }
}
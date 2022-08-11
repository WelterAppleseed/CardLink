package com.example.cardlinker.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.cardlinker.R
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

        userAppearanceViewModel.getIsFirstTimeUsed().observe(this) { firstTime ->
            if (firstTime) {
                navigationViewModel.goToEnterBannersFragment()
            } else {
                navigationViewModel.goToUserCardsFragment()
            }
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
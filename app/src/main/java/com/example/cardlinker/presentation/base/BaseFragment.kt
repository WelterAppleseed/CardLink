package com.example.cardlinker.presentation.base

import android.app.AlertDialog
import android.app.StatusBarManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.cardlinker.R
import com.example.cardlinker.presentation.activities.MainActivity
import com.example.cardlinker.util.objects.BalloonConstants
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation

open class BaseFragment<V : ViewBinding>(
    private val binder: (LayoutInflater, ViewGroup?, Boolean) -> V
) : Fragment() {

    private var contentBinding: V? = null

    protected open var bottomNavigationViewVisibility = View.VISIBLE
    protected open var windowSoftInput = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    protected open var topBarColor = R.color.toolbar_background_color
    protected val binding: V
        get() = requireNotNull(contentBinding) {
            "Binding is only valid between onCreateView and onDestroyView"
        }

    override fun onStart() {
        super.onStart()
        bottomNavigationBarVisibility(bottomNavigationViewVisibility)
        liftViewsWithKeyboard(requireActivity().window)
        context?.let { it1 -> ContextCompat.getColor(it1, topBarColor) }
            ?.let { it2 -> drawStatusBar(it2, false) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentBinding = binder.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        contentBinding = null
        super.onDestroyView()
    }

    fun createCustomDialog(
        vb: ViewBinding,
        unit: ((thisViewBinding: ViewBinding, dialog: AlertDialog) -> Unit)?
    ): AlertDialog {
        val dialog = AlertDialog.Builder(this.context, R.style.AlertDialogCustom)
            .setView(vb.root)
            .setCancelable(true)
            .create()
        if (unit != null) {
            unit(vb, dialog)
        }
        return dialog
    }

    fun drawStatusBar(@ColorInt color: Int, isWhiteText: Boolean) {
        val window = activity?.window
        val decorView = window?.decorView
        if (window != null && decorView != null) {
            val wic = WindowInsetsControllerCompat(
                window,
                decorView
            )
            wic.isAppearanceLightStatusBars = isWhiteText
            window.statusBarColor = color
        }

    }

    private fun bottomNavigationBarVisibility(visibility: Int) {
        if (activity is MainActivity) {
            (activity as MainActivity).setBottomNavigationBarVisibility(visibility)
        }
    }
    fun getToolTip(text: String?, arrowOrientation: ArrowOrientation, arrowPosition: Float): Balloon? {
        return if (text != null) Balloon.Builder(requireContext())
            .setArrowSize(BalloonConstants.ARROW_SIZE)
            .setArrowOrientation(arrowOrientation)
            .setArrowPosition(arrowPosition)
            .setCornerRadius(BalloonConstants.CORNER_RADIUS)
            .setText(text)
            .setMargin(BalloonConstants.MARGIN)
            .setPadding(BalloonConstants.PADDING)
            .setBackgroundColorResource(R.color.attent_blue_color)
            .setLifecycleOwner(viewLifecycleOwner)
            .setBalloonAnimation(BalloonAnimation.FADE)
            .setFocusable(false)
            .build()
        else {
            null
        }
    }
    fun hideKeyboard() {
        val imm: InputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun liftViewsWithKeyboard(window: Window) {
        window.setSoftInputMode(windowSoftInput)
    }
}
package com.example.cardlinker.presentation.base

import android.app.AlertDialog
import android.app.StatusBarManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.cardlinker.R


open class BaseFragment<V : ViewBinding>(
    private val binder: (LayoutInflater, ViewGroup?, Boolean) -> V
) : Fragment() {

    private var contentBinding: V? = null

    protected val binding: V
        get() = requireNotNull(contentBinding) {
            "Binding is only valid between onCreateView and onDestroyView"
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

    fun createCustomDialog(vb: ViewBinding, unit: ((thisViewBinding: ViewBinding, dialog: AlertDialog) -> Unit)?): AlertDialog {
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

}
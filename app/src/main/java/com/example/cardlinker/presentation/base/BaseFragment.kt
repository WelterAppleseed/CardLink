package com.example.cardlinker.presentation.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.cardlinker.R

open class BaseFragment<V : ViewBinding>(
    private val binder: (LayoutInflater, ViewGroup?, Boolean) -> V
) : Fragment() {

    private var contentBinding: V? = null

    private var dialog: AlertDialog? = null
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

    fun getCustomDialog() = dialog

    fun initCustomDialog(vb: ViewBinding, unit: ((thisViewBinding: ViewBinding) -> Unit)?) {
        dialog = AlertDialog.Builder(this.context, R.style.AlertDialogCustom)
            .setView(vb.root)
            .setCancelable(true)
            .create()
        if (unit != null) {
            unit(vb)
        }
    }

}
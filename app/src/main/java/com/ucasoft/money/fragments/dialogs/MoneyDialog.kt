package com.ucasoft.money.fragments.dialogs

import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.ucasoft.money.listeners.DialogListener

abstract class MoneyDialog: DialogFragment() {

    protected var listener: DialogListener? = null

    open var viewResourceId: Int = 0

    private var _customView: View? = null

    protected val customView: View
    get(){
        if (_customView == null){
            _customView = activity.layoutInflater.inflate(viewResourceId, null)
        }
        return _customView!!
    }

    fun setDialogListener(dialogListener: DialogListener){
        listener = dialogListener
    }

    protected fun buildDialog(titleId: Int): AlertDialog {
        return buildDialog(getString(titleId))
    }

    protected fun buildDialog(title: String): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        builder.setView(customView)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (valid()) {
                    positiveButtonListener()
                }
            }
        }
        return dialog
    }

    protected open fun valid(): Boolean {
        return true
    }

    protected abstract fun positiveButtonListener()
}
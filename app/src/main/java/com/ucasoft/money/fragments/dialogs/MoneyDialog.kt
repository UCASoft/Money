package com.ucasoft.money.fragments.dialogs

import android.support.v4.app.DialogFragment
import android.view.View
import com.ucasoft.money.listeners.DialogListener

open class MoneyDialog: DialogFragment() {

    protected var listener: DialogListener? = null

    open var viewResourceId: Int = 0

    protected fun inflateView(): View {
        val inflater = activity.layoutInflater
        return inflater.inflate(viewResourceId, null)
    }

    fun setDialogListener(dialogListener: DialogListener){
        listener = dialogListener
    }
}
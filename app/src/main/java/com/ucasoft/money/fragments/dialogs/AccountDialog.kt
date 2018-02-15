package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class AccountDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(arguments.getString("title"))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, {dialog, id -> this.dialog.cancel() })
        return builder.create()
    }

    companion object {
        const val DialogName = "AccountDialog"
    }
}
package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.ucasoft.money.R

class AccountDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_account, null)
        val accountTypeSpinner = view.findViewById(R.id.dialog_account_type) as Spinner
        val accountBankLayout = view.findViewById(R.id.dialog_bank_layout) as LinearLayout
        accountTypeSpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.account_types))
        accountTypeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    accountBankLayout.visibility = View.GONE
                } else {
                    accountBankLayout.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        builder.setView(view)
                .setTitle(arguments.getString("title"))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, {dialog, id -> this.dialog.cancel() })
        return builder.create()
    }

    companion object {
        const val DialogName = "AccountDialog"
    }
}
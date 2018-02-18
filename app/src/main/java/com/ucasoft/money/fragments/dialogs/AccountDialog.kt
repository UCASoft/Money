package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.ucasoft.money.R
import com.ucasoft.money.adapters.CardViewAdapter
import com.ucasoft.money.adapters.CurrencyViewAdapter
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount
import java.io.Serializable

class AccountDialog : DialogFragment() {

    private var account: Serializable? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.dialog_account, null)
        val holder = DialogHolder(view)
        holder.accountTypeSpinner.adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.account_types))
        holder.accountTypeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    holder.accountBankLayout.visibility = View.GONE
                } else {
                    holder.accountBankLayout.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        account = arguments.getSerializable(DialogItem)

        if (account != null){
            assignAccount(holder)
        }

        builder.setView(view)
                .setTitle(arguments.getString(DialogTitleKey))
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, {dialog, id -> this.dialog.cancel() })
        return builder.create()
    }

    private fun assignAccount(holder: DialogHolder) {
        val isBankAccount = account is MoneyBankAccount
        if (isBankAccount) {
            holder.accountTypeSpinner.setSelection(1)
        } else {
            holder.accountTypeSpinner.setSelection(0)
        }
        holder.accountNameView.setText((account as MoneyAccount).name)
        val currencyViewAdapter = CurrencyViewAdapter(context, (account as MoneyAccount).currencies)
        for (i in 0 until currencyViewAdapter.count) {
            holder.accountCurrenciesLayout.addView(currencyViewAdapter.getView(i))
        }
        if (isBankAccount && (account as MoneyBankAccount).cards != null) {
            val cardViewAdapter = CardViewAdapter(context, R.layout.card, (account as MoneyBankAccount).cards!!)
            for (i in 0 until cardViewAdapter.count) {
                holder.accountCardLayout.addView(cardViewAdapter.getView(i))
            }
        }
    }

    companion object {
        const val DialogName = "AccountDialog"
        const val DialogTitleKey = "${DialogName}Title"
        const val DialogItem = "${DialogName}Item"
    }

    inner class DialogHolder(view: View) {
        val accountTypeSpinner = view.findViewById(R.id.dialog_account_type) as Spinner
        val accountBankLayout = view.findViewById(R.id.dialog_bank_layout) as LinearLayout
        val accountNameView = view.findViewById(R.id.dialog_account_name) as EditText
        val accountCurrenciesLayout = view.findViewById(R.id.dialog_currencies_layout) as LinearLayout
        val accountCardLayout = view.findViewById(R.id.dialog_cards_layout) as LinearLayout
    }
}
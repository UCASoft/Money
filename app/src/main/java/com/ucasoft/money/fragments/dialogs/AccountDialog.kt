package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.*
import com.ucasoft.controls.AdapterLinearLayout
import com.ucasoft.money.R
import com.ucasoft.money.adapters.CardViewAdapter
import com.ucasoft.money.adapters.CurrencyViewAdapter
import com.ucasoft.money.listeners.DialogListener
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount
import java.io.Serializable

class AccountDialog : DialogFragment(), DialogListener {

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is CurrencyDialog){
            (account as MoneyAccount).currencies.add(dialog.currency!!)
            currencyViewAdapter.notifyDataSetChanged()
        }
    }

    private var account: Serializable? = null

    private lateinit var currencyViewAdapter: CurrencyViewAdapter

    private lateinit var cardViewAdapter: CardViewAdapter


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

        holder.addCurrencyButton.setOnClickListener {
            val currencyDialog = CurrencyDialog()
            currencyDialog.setDialogListener(this)
            currencyDialog.show(fragmentManager, CurrencyDialog.DialogName)
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
        holder.accountTypeSpinner.isEnabled = false
        holder.accountNameView.setText((account as MoneyAccount).name)
        currencyViewAdapter = CurrencyViewAdapter(context, (account as MoneyAccount).currencies)
        holder.accountCurrenciesLayout.setAdapter(currencyViewAdapter)
        if (isBankAccount && (account as MoneyBankAccount).cards != null) {
            holder.accountCardLayout.setAdapter(CardViewAdapter(context, R.layout.card, (account as MoneyBankAccount).cards!!))
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
        val accountCurrenciesLayout = view.findViewById(R.id.dialog_currencies_layout) as AdapterLinearLayout
        val accountCardLayout = view.findViewById(R.id.dialog_cards_layout) as AdapterLinearLayout
        val addCurrencyButton = view.findViewById(R.id.dialog_add_currency_button) as Button
    }
}
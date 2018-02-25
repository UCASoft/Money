package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.*
import com.ucasoft.controls.AdapterLinearLayout
import com.ucasoft.money.R
import com.ucasoft.money.adapters.CardViewAdapter
import com.ucasoft.money.adapters.CurrencyViewAdapter
import com.ucasoft.money.listeners.DialogListener
import com.ucasoft.money.model.Bank
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount

class AccountDialog : MoneyDialog(), DialogListener {

    override var viewResourceId: Int = R.layout.dialog_account

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is CurrencyDialog){
            account?.currencies?.add(dialog.currency!!)
            holder.accountCurrenciesLayout.error = null
            currencyViewAdapter.notifyDataSetChanged()
        } else if(dialog is CardDialog){
            (account as MoneyBankAccount).cards.add(dialog.card!!)
            cardViewAdapter.notifyDataSetChanged()
        }
    }

    private var account: MoneyAccount? = null

    private lateinit var currencyViewAdapter: CurrencyViewAdapter

    private lateinit var cardViewAdapter: CardViewAdapter

    private var mode: DialogMode = DialogMode.Add

    private lateinit var holder: DialogHolder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = customView
        holder = DialogHolder(view)
        holder.accountTypeSpinner.adapter = ArrayAdapter.createFromResource(context, R.array.account_types, android.R.layout.simple_list_item_1)
        holder.accountTypeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0){
                    holder.accountBankLayout.visibility = View.GONE
                } else {
                    holder.accountBankLayout.visibility = View.VISIBLE
                }

                if (mode == DialogMode.Add) {
                    account = if (position == 0){
                        MoneyAccount.newInstance(R.drawable.ic_wallet_color_48dp, "", ArrayList())
                    } else {
                        MoneyBankAccount.newInstance(R.drawable.ic_bank_color_48dp, "", ArrayList(), Bank.newInstance("ВТБ"), ArrayList())
                    }
                    initAdapters(holder)
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

        holder.addCardButton.setOnClickListener{
            val cardDialog = CardDialog()
            cardDialog.setDialogListener(this)
            cardDialog.show(fragmentManager, CardDialog.DialogName)
        }

        account = arguments.getSerializable(DialogItem) as MoneyAccount?

        if (account != null){
            mode = DialogMode.Edit
            assignAccount(holder)
        }

        return buildDialog(arguments.getString(DialogTitleKey))
    }

    override fun valid(): Boolean {
        holder.accountNameView.error = null
        holder.accountCurrenciesLayout.error = null
        var isError = false
        if (holder.accountNameView.text.isEmpty()){
            holder.accountNameView.error = getString(R.string.dialog_required_field)
            isError = true
        }
        if (holder.accountCurrenciesLayout.childCount == 0){
            holder.accountCurrenciesLayout.error = getString(R.string.dialog_currency_empty_error)
            isError = true
        }
        return !isError
    }

    override fun positiveButtonListener() {
        account!!.name = holder.accountNameView.text.toString()
        arguments.putSerializable(DialogItem, account)
        listener?.onDialogPositiveClick(this)
        this.dialog.dismiss()
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
        initAdapters(holder)
    }

    private fun initAdapters(holder: DialogHolder){
        currencyViewAdapter = CurrencyViewAdapter(context, account!!.currencies)
        holder.accountCurrenciesLayout.setAdapter(currencyViewAdapter)
        if (account is MoneyBankAccount){
            cardViewAdapter = CardViewAdapter(context, R.layout.card, (account as MoneyBankAccount).cards)
            holder.accountCardLayout.setAdapter(cardViewAdapter)
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
        val addCardButton = view.findViewById(R.id.dialog_add_card_button) as Button
    }
}
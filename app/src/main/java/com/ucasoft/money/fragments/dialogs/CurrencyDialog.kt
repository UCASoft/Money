package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.ucasoft.money.R
import com.ucasoft.money.model.MoneyCurrency

class CurrencyDialog : MoneyDialog() {

    var currency: MoneyCurrency? = null

    override var viewResourceId: Int = R.layout.dialog_currency

    private lateinit var holder: DialogHolder

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = customView
        holder = DialogHolder(view)
        holder.currencyCodeText.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, MoneyCurrency.Symbols.keys.toList()))

       return buildDialog(R.string.add_currency_title)
    }

    override fun positiveButtonListener() {
        currency = MoneyCurrency.newInstance(holder.currencyBalanceEdit.text.toString().toDouble(), holder.currencyCodeText.text.toString())
        listener?.onDialogPositiveClick(this)
        dismiss()
    }

    companion object {
        const val DialogName = "CurrencyDialog"
    }

    inner class DialogHolder(view: View){
        val currencyCodeText = view.findViewById(R.id.add_currency_code) as AutoCompleteTextView
        val currencyBalanceEdit = view.findViewById(R.id.add_currency_balance) as TextInputEditText
    }
}
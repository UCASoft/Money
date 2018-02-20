package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.ucasoft.money.R
import com.ucasoft.money.listeners.DialogListener
import com.ucasoft.money.model.MoneyCurrency

class CurrencyDialog : DialogFragment() {

    private lateinit var listener: DialogListener

    var currency: MoneyCurrency? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater

        val view = inflater.inflate(R.layout.dialog_currency, null)
        val holder = DialogHolder(view)
        holder.currencyCodeText.setAdapter(ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, MoneyCurrency.Symbols.keys.toList()))

        builder.setView(view)
                .setTitle(R.string.add_currency_title)
                .setPositiveButton(android.R.string.ok) { dialogInterface, id ->
                    currency = MoneyCurrency(holder.currencyBalanceEdit.text.toString().toDouble(), holder.currencyCodeText.text.toString())
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

    fun setDialogListener(dialogListener: DialogListener){
        listener = dialogListener;
    }

    companion object {
        const val DialogName = "CurrencyDialog"
    }

    inner class DialogHolder(view: View){
        val currencyCodeText = view.findViewById(R.id.add_currency_code) as AutoCompleteTextView
        val currencyBalanceEdit = view.findViewById(R.id.add_currency_balance) as TextInputEditText
    }
}
package com.ucasoft.money.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Spinner
import com.ucasoft.money.R
import com.ucasoft.money.adapters.PaymentSystemViewAdapter
import com.ucasoft.money.model.Card
import com.ucasoft.money.model.PaymentSystem

class CardDialog : MoneyDialog() {

    var card: Card? = null

    override var viewResourceId: Int = R.layout.dialog_card

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = inflateView()
        val holder = DialogHolder(view)

        val systemNames = resources.getStringArray(R.array.payment_system_names)
        val systemLogos = resources.obtainTypedArray(R.array.payment_system_logos)
        val paymentSystems =  ArrayList<PaymentSystem>()

        (0 until systemNames.size).mapTo(paymentSystems) { PaymentSystem.newInstance(systemLogos.getResourceId(it, 0), systemNames[it]) }

        systemLogos.recycle()
        val adapter = PaymentSystemViewAdapter(context, R.layout.payment_system, paymentSystems)
        holder.cardSystem.adapter = adapter

        builder.setView(view)
                .setTitle(R.string.add_card_title)
                .setPositiveButton(android.R.string.ok) { dialog, id ->
                    card = Card.newInstance((holder.cardSystem.selectedItem as PaymentSystem).logoResource, holder.cardDigits.text.toString())
                    listener?.onDialogPositiveClick(this)
                }
                .setNegativeButton(android.R.string.cancel, null)

        return builder.create()
    }

    companion object {
        const val DialogName = "CardDialog"
    }

    inner class DialogHolder(view: View){
        val cardSystem = view.findViewById(R.id.dialog_card_system) as Spinner
        val cardDigits = view.findViewById(R.id.dialog_card_digits) as TextInputEditText
    }
}
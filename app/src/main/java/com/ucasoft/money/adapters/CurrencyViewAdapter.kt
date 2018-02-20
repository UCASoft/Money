package com.ucasoft.money.adapters

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ucasoft.money.model.MoneyCurrency

class CurrencyViewAdapter(context: Context?, private var currencies: List<MoneyCurrency>) : ArrayAdapter<MoneyCurrency>(context, 0, currencies) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = convertView ?: TextView(context)
        val resultView = view as TextView
        resultView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        resultView.gravity = Gravity.RIGHT
        val holder = ViewHolder(resultView)
        val currency = currencies[position]
        holder.item = currency
        holder.text = currency.toString()
        return resultView
    }

    inner class ViewHolder(private val view: View) {
        var item: MoneyCurrency? = null
        var text: String = ""
            set(value){
                (view as TextView).text = value
            }
    }
}
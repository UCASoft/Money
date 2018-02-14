package com.ucasoft.money.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ucasoft.money.R
import com.ucasoft.money.model.Card

class MoneyCardViewAdapter(context: Context?, var resource: Int, var cards: List<Card>): ArrayAdapter<Card>(context, resource, cards) {

    private var inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val resultView : View = convertView ?: inflater.inflate(resource, null)
        val holder : ViewHolder
        holder = ViewHolder(resultView)
        val card = cards[position]
        holder.item = card
        holder.logoView.setImageResource(card.logoResource)
        holder.cardNumberView.text = card.number
        return resultView
    }

    inner class ViewHolder(view: View) {
        val logoView: ImageView = view.findViewById(R.id.card_logo) as ImageView
        val cardNumberView: TextView = view.findViewById(R.id.card_number) as TextView
        var item: Card? = null

    }
}
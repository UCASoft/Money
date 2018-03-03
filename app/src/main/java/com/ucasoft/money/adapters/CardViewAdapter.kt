package com.ucasoft.money.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ucasoft.money.R
import com.ucasoft.money.model.Card

class CardViewAdapter(context: Context?, resource: Int, cards: List<Card>): InflaterAdapter<Card, CardViewAdapter.ViewHolder>(context, resource, cards) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val resultView = super.getView(position, convertView, parent)
        val card = getItem(position)
        holder?.item = card
        holder?.logoView?.setImageResource(card.logoResource)
        holder?.cardNumberView?.text = card.number
        return resultView
    }

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) {
        val logoView: ImageView = view.findViewById(R.id.card_logo) as ImageView
        val cardNumberView: TextView = view.findViewById(R.id.card_number) as TextView
        var item: Card? = null

    }
}
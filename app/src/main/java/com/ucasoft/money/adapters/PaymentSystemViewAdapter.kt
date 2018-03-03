package com.ucasoft.money.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ucasoft.money.R
import com.ucasoft.money.model.PaymentSystem

class PaymentSystemViewAdapter(context: Context?, resource: Int, systems: List<PaymentSystem>) : InflaterAdapter<PaymentSystem, PaymentSystemViewAdapter.ViewHolder>(context, resource, systems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val resultView = super.getView(position, convertView, parent)
        val system = getItem(position)
        holder?.logoView?.setImageResource(system.logoResource)
        holder?.nameView?.text = system.name
        return resultView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    override fun createViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) {
        val logoView: ImageView = view.findViewById(R.id.system_logo) as ImageView
        val nameView: TextView = view.findViewById(R.id.system_name) as TextView
        var item: PaymentSystem? = null

    }
}
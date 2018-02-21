package com.ucasoft.money.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ucasoft.money.R
import com.ucasoft.money.model.PaymentSystem

class PaymentSystemViewAdapter(context: Context?, var resource: Int, private var systems: List<PaymentSystem>) : ArrayAdapter<PaymentSystem>(context, resource, systems) {

    private var inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val resultView : View = convertView ?: inflater.inflate(resource, null)
        val holder = ViewHolder(resultView)
        val system = systems[position]
        holder.logoView.setImageResource(system.logoResource)
        holder.nameView.text = system.name
        return resultView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }

    inner class ViewHolder(view: View) {
        val logoView: ImageView = view.findViewById(R.id.system_logo) as ImageView
        val nameView: TextView = view.findViewById(R.id.system_name) as TextView
        var item: PaymentSystem? = null

    }
}
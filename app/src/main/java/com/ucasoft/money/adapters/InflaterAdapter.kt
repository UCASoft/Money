package com.ucasoft.money.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

abstract class InflaterAdapter<T, VH>(context: Context?, val resource: Int, items: List<T>) : ArrayAdapter<T>(context, resource, items) {

    private var inflater : LayoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var holder : VH? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val resultView : View
        if (convertView == null){
            resultView = inflater.inflate(resource, parent, false)
            holder = createViewHolder(resultView)
            resultView.tag = holder
        } else{
            resultView = convertView
            holder = resultView.tag as VH
        }

        return resultView
    }

    abstract fun createViewHolder(view: View): VH
}
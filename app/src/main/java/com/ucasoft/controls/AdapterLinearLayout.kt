package com.ucasoft.controls

import android.content.Context
import android.database.DataSetObserver
import android.util.AttributeSet
import android.widget.Adapter
import android.widget.LinearLayout

class AdapterLinearLayout : LinearLayout {

    private var adapter: Adapter? = null

    private val dataSetObserver = object: DataSetObserver(){

        override fun onChanged() {
            reloadChildren()
        }

        override fun onInvalidated() {
            removeAllViews()
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setAdapter(adapter: Adapter?){
        if (this.adapter == adapter){
            return
        }
        this.adapter?.unregisterDataSetObserver(dataSetObserver)
        this.adapter = adapter
        this.adapter?.registerDataSetObserver(dataSetObserver)
        reloadChildren()
    }

    private fun reloadChildren() {
        removeAllViews()
        if (adapter == null){
            return
        }
        (0 until adapter!!.count)
                .mapNotNull { adapter?.getView(it, null, this) }
                .forEach { addView(it) }
        requestLayout()
    }
}
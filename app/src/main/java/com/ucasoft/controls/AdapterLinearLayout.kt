package com.ucasoft.controls

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView

class AdapterLinearLayout : LinearLayout {

    private var adapter: Adapter? = null

    var error: CharSequence? = null
    set(value) {
        field = value
        reloadChildren()
    }

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
        if (error != null){
            val textView = TextView(context)
            textView.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.RED)
            textView.text = error
            addView(textView)
        }
        if (adapter == null){
            return
        }
        (0 until adapter!!.count)
                .mapNotNull { adapter?.getView(it, null, this) }
                .forEach { addView(it) }

        requestLayout()
    }
}
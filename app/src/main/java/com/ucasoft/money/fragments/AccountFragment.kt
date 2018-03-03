package com.ucasoft.money.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ucasoft.money.R
import com.ucasoft.money.adapters.MoneyAccountViewAdapter
import com.ucasoft.money.dummy.DummyContent
import com.ucasoft.money.fragments.dialogs.AccountDialog
import com.ucasoft.money.listeners.AdapterChangeModeListener
import com.ucasoft.money.listeners.DialogListener
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyCurrency

/**
 * A fragment representing a list of Items.
 */
class AccountFragment: Fragment(), AdapterChangeModeListener, DialogListener {

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is AccountDialog){
            content.MoneyAccounts.add(dialog.arguments.getSerializable(AccountDialog.DialogItem) as MoneyAccount)
            adapter.notifyDataSetChanged()
        }
    }

    private val opened = HashSet<Int>()

    lateinit var floatButton: FloatingActionButton

    override fun editMode(position: Int) {
        opened.add(position)
        if (opened.size == 1) {
            floatButton.animate().alpha(0.0f).duration = 150
            floatButton.visibility = View.GONE
        }
    }

    override fun normalMode(position: Int) {
        opened.remove(position)
        if (opened.size == 0) {
            floatButton.animate().alpha(1.0f).duration = 150
        }
    }

    override fun editModeStart(position: Int) {
        if (opened.size == 0) {
            floatButton.visibility = View.VISIBLE
            floatButton.animate().alpha(0.5f).duration = 150
        }
    }

    override fun normalModeStart(position: Int) {
        if(opened.size == 1) {
            floatButton.visibility = View.VISIBLE
            floatButton.animate().alpha(0.5f).duration = 150
        }
    }

    private lateinit var content: DummyContent

    private lateinit var adapter: MoneyAccountViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)

        val recyclerView = view.findViewById(R.id.account_list) as RecyclerView
        content = DummyContent(this.context)
        adapter = MoneyAccountViewAdapter(content.MoneyAccounts)
        adapter.changeModeListener = this
        recyclerView.adapter = adapter

        val totalLayout = view.findViewById(R.id.total_balance_layout) as LinearLayout
        val totalView = view.findViewById(R.id.total_balance_view) as TextView
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val homeCurrency = preferences.getString("home_currency", "")
        if (homeCurrency.isEmpty()){
            totalLayout.visibility = View.GONE
        } else {
            val totalString = "${String.format("%.2f", content.MoneyAccounts.getTotal())} ${MoneyCurrency.Symbols[homeCurrency]}"
            totalView.text = totalString
        }

        floatButton = view.findViewById(R.id.account_add) as FloatingActionButton
        floatButton.setOnClickListener{
            val dialog = AccountDialog()
            val bundle = Bundle()
            bundle.putString(AccountDialog.DialogTitleKey, this.getString(R.string.add_account_title))
            dialog.arguments = bundle
            dialog.setDialogListener(this)
            dialog.show(fragmentManager, AccountDialog.DialogName)
        }
        return view
    }

    companion object {

        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}
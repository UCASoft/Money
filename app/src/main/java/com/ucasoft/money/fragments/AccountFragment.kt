package com.ucasoft.money.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ucasoft.money.R
import com.ucasoft.money.adapters.MoneyAccountViewAdapter
import com.ucasoft.money.dummy.DummyContent
import com.ucasoft.money.fragments.dialogs.AccountDialog
import com.ucasoft.money.listeners.AdapterChangeModeListener

/**
 * A fragment representing a list of Items.
 */
class AccountFragment: Fragment(), AdapterChangeModeListener {
    override fun editMode() {
        floatButton.animate().alpha(0.0f).duration = 150
    }

    override fun normalMode() {
        floatButton.animate().alpha(1.0f).duration = 150
    }

    lateinit var floatButton: FloatingActionButton

    override fun editModeStart() {
        floatButton.animate().alpha(0.5f).duration = 150
    }

    override fun normalModeStart() {
        floatButton.animate().alpha(0.5f).duration = 150
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)

        val recyclerView = view.findViewById(R.id.list)
        if (recyclerView is RecyclerView) {
            val dummyContent = DummyContent(this.context)
            val adapter = MoneyAccountViewAdapter(dummyContent.MoneyAccounts)
            adapter.editModeListener = this
            recyclerView.adapter = adapter
        }

        floatButton = view.findViewById(R.id.account_add) as FloatingActionButton
        floatButton.setOnClickListener({
            run {
                val dialog = AccountDialog()
                val bundle = Bundle()
                bundle.putString(AccountDialog.DialogTitleKey, this.getString(R.string.add_account_title))
                dialog.arguments = bundle
                dialog.show(fragmentManager, AccountDialog.DialogName)
            }
        })
        return view
    }

    companion object {

        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}
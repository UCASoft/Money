package com.ucasoft.money.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ucasoft.money.R
import com.ucasoft.money.adapters.MoneyAccountViewAdapter
import com.ucasoft.money.dummy.DummyContent
import com.ucasoft.money.fragments.dialogs.AccountDialog

/**
 * A fragment representing a list of Items.
 */
class AccountFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)

        val recyclerView = view.findViewById(R.id.list)
        if (recyclerView is RecyclerView) {
            val dummyContent = DummyContent(this.context)
            recyclerView.adapter = MoneyAccountViewAdapter(dummyContent.MoneyAccounts)
        }

        val floatButton = view.findViewById(R.id.account_add)
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
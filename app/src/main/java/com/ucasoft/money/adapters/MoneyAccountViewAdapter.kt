package com.ucasoft.money.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.ucasoft.money.R

import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount

/**
 * [RecyclerView.Adapter] that can display a [MoneyAccount]
 */
 class MoneyAccountViewAdapter(private val accounts:List<MoneyAccount>):RecyclerView.Adapter<MoneyAccountViewAdapter.ViewHolder>() {

    private var context : Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.item = account
        holder.logoView.setImageResource(account.logoResource)
        holder.accountNameView.text = account.name
        if (account is MoneyBankAccount) {
            holder.cardListView.adapter = MoneyCardViewAdapter(context, R.layout.card, account.cards)
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val logoView: ImageView = mView.findViewById(R.id.account_logo) as ImageView
        val accountNameView: TextView = mView.findViewById(R.id.content) as TextView
        val cardListView : ListView = mView.findViewById(R.id.account_cards) as ListView
        var item: MoneyAccount? = null

        override fun toString(): String {
            return super.toString() + " '" + accountNameView.text + "'"
        }
    }
}

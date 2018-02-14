package com.ucasoft.money.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ucasoft.money.R

import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount

/**
 * [RecyclerView.Adapter] that can display a [MoneyAccount]
 */
 class MoneyAccountViewAdapter(private val accounts:List<MoneyAccount>):RecyclerView.Adapter<MoneyAccountViewAdapter.ViewHolder>() {

    private var context : Context? = null

    private lateinit var inflater : LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = accounts[position]
        holder.item = account
        holder.logoView.setImageResource(account.logoResource)
        holder.accountNameView.text = account.name
        if (account is MoneyBankAccount) {
            holder.bankNameView.text = account.bank.name
            if (account.cards != null) {
                val cardAdapter = MoneyCardViewAdapter(context, R.layout.card, account.cards!!)
                for (i in 0 until cardAdapter.count) {
                    holder.cardsView.addView(cardAdapter.getView(i, null, null))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logoView: ImageView = view.findViewById(R.id.account_logo) as ImageView
        val bankNameView: TextView = view.findViewById(R.id.bank_name) as TextView
        val accountNameView: TextView = view.findViewById(R.id.account_name) as TextView
        val cardsView: LinearLayout = view.findViewById(R.id.account_cards) as LinearLayout
        var item: MoneyAccount? = null

        override fun toString(): String {
            return super.toString() + " '" + accountNameView.text + "'"
        }
    }
}

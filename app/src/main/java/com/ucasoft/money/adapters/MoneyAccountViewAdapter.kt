package com.ucasoft.money.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.ucasoft.money.R
import com.ucasoft.money.fragments.dialogs.AccountDialog

import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount

/**
 * [RecyclerView.Adapter] that can display a [MoneyAccount]
 */
 class MoneyAccountViewAdapter(private val accounts:List<MoneyAccount>):RecyclerSwipeAdapter<MoneyAccountViewAdapter.ViewHolder>() {

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.account_swipe
    }

    private lateinit var context : Context

    private lateinit var inflater : LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.account_swipe_delete))
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.account_swipe_edit))

        holder.editButton.setOnClickListener({ view ->
            val dialog = AccountDialog()
            val bundle = Bundle()
            bundle.putString(AccountDialog.DialogTitleKey, context.getString(R.string.edit_account_title))
            bundle.putSerializable(AccountDialog.DialogItem, holder.item)
            dialog.arguments = bundle
            dialog.show((context as AppCompatActivity).supportFragmentManager, AccountDialog.DialogName)})

        val account = accounts[position]
        holder.item = account
        holder.logoView.setImageResource(account.logoResource)
        holder.accountNameView.text = account.name
        val currencyAdapter = CurrencyViewAdapter(context, account.currencies)
        for (i in 0 until currencyAdapter.count){
            holder.currenciesView.addView(currencyAdapter.getView(i))
        }
        if (account is MoneyBankAccount) {
            holder.bankNameView.text = account.bank.name
            if (account.cards != null) {
                val cardAdapter = CardViewAdapter(context, R.layout.card, account.cards!!)
                for (i in 0 until cardAdapter.count) {
                    holder.cardsView.addView(cardAdapter.getView(i))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val swipeLayout: SwipeLayout = view.findViewById(R.id.account_swipe) as SwipeLayout
        val editButton: ImageView = view.findViewById(R.id.account_edit_button) as ImageView
        //val deleteButton: ImageView = view.findViewById(R.id.account_delete_button) as ImageView
        val logoView: ImageView = view.findViewById(R.id.account_logo) as ImageView
        val bankNameView: TextView = view.findViewById(R.id.bank_name) as TextView
        val accountNameView: TextView = view.findViewById(R.id.account_name) as TextView
        val currenciesView: LinearLayout = view.findViewById(R.id.account_currencies) as LinearLayout
        val cardsView: LinearLayout = view.findViewById(R.id.account_cards) as LinearLayout
        var item: MoneyAccount? = null

        override fun toString(): String {
            return super.toString() + " '" + accountNameView.text + "'"
        }
    }
}

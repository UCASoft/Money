package com.ucasoft.money.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.ucasoft.controls.AdapterLinearLayout
import com.ucasoft.money.R
import com.ucasoft.money.fragments.dialogs.AccountDialog
import com.ucasoft.money.listeners.AdapterChangeModeListener
import com.ucasoft.money.listeners.DialogListener

import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount

/**
 * [RecyclerView.Adapter] that can display a [MoneyAccount]
 */
 class MoneyAccountViewAdapter(private val accounts:ArrayList<MoneyAccount>):RecyclerSwipeAdapter<MoneyAccountViewAdapter.ViewHolder>(), DialogListener {

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is AccountDialog){
            this.notifyDataSetChanged()
        }
    }

    var changeModeListener: AdapterChangeModeListener? = null

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
        holder.swipeLayout.addSwipeListener(object: SwipeLayout.SwipeListener{

            override fun onOpen(layout: SwipeLayout) {
                if (changeModeListener != null) {
                    if (layout.dragEdge == SwipeLayout.DragEdge.Right) {
                        changeModeListener!!.editMode(holder.adapterPosition)
                    }
                }
            }

            override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
            }

            override fun onStartOpen(layout: SwipeLayout) {
                if (changeModeListener != null){
                    if (layout.dragEdge == SwipeLayout.DragEdge.Right) {
                        changeModeListener!!.editModeStart(holder.adapterPosition)
                    }
                }
            }

            override fun onStartClose(layout: SwipeLayout) {
                if (changeModeListener != null){
                    if (layout.dragEdge == SwipeLayout.DragEdge.Right) {
                        changeModeListener!!.normalModeStart(holder.adapterPosition)
                    }
                }
            }

            override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
            }

            override fun onClose(layout: SwipeLayout) {
                if (changeModeListener != null){
                    if (layout.dragEdge == SwipeLayout.DragEdge.Right) {
                        changeModeListener!!.normalMode(holder.adapterPosition)
                    }
                }
            }
        })

        holder.editButton.setOnClickListener{
            val dialog = AccountDialog()
            val bundle = Bundle()
            bundle.putString(AccountDialog.DialogTitleKey, context.getString(R.string.edit_account_title))
            bundle.putSerializable(AccountDialog.DialogItem, holder.item)
            dialog.arguments = bundle
            dialog.setDialogListener(this)
            dialog.show((context as AppCompatActivity).supportFragmentManager, AccountDialog.DialogName)
        }

        val account = accounts[position]
        holder.item = account
        holder.logoView.setImageResource(account.logoResource)
        holder.accountNameView.text = account.name
        holder.currenciesView.setAdapter(CurrencyViewAdapter(context, account.currencies))
        if (account is MoneyBankAccount) {
            holder.bankNameView.text = account.bank.name
            if (account.cards != null) {
                holder.cardsView.setAdapter(CardViewAdapter(context, R.layout.card, account.cards!!))
            }
        } else {
            holder.bankNameView.text = ""
            holder.cardsView.setAdapter(null)
        }
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val swipeLayout = view.findViewById(R.id.account_swipe) as SwipeLayout
        val editButton = view.findViewById(R.id.account_edit_button) as ImageView
        //val deleteButton = view.findViewById(R.id.account_delete_button) as ImageView
        val logoView = view.findViewById(R.id.account_logo) as ImageView
        val bankNameView = view.findViewById(R.id.bank_name) as TextView
        val accountNameView = view.findViewById(R.id.account_name) as TextView
        val currenciesView = view.findViewById(R.id.account_currencies) as AdapterLinearLayout
        val cardsView = view.findViewById(R.id.account_cards) as AdapterLinearLayout
        var item: MoneyAccount? = null

        override fun toString(): String {
            return super.toString() + " '" + accountNameView.text + "'"
        }
    }
}

package com.ucasoft.money.model

class MoneyBankAccount : MoneyAccount() {

    lateinit var cards: MutableList<MoneyCard>

    companion object {

        fun newInstance(logoResource : Int, name : String, cards: ArrayList<MoneyCard>) : MoneyAccount {
            val account = MoneyBankAccount()
            account.logoResource = logoResource
            account.name = name
            account.cards = cards
            return account
        }
    }
}
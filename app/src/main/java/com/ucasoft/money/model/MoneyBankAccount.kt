package com.ucasoft.money.model

class MoneyBankAccount : MoneyAccount() {

    lateinit var bank: Bank
    var cards: MutableList<Card>? = null

    companion object {

        fun newInstance(logoResource : Int, name : String, currencies: ArrayList<MoneyCurrency>, bank: Bank, cards: ArrayList<Card>?) : MoneyAccount {
            val account = MoneyBankAccount()
            account.logoResource = logoResource
            account.name = name
            account.bank = bank
            account.currencies = currencies
            account.cards = cards
            return account
        }
    }
}
package com.ucasoft.money.model

import com.ucasoft.money.dummy.DummyContent

class MoneyAccounts(private val homeCurrency: String) : ArrayList<MoneyAccount>() {

    fun getTotal(): Double{
        if (!homeCurrency.isEmpty()) {
            var total = 0.0
            for (i in 0 until this.size){
                val account = this[i]
                for (j in 0 until account.currencies.size){
                    val currency = account.currencies[j]
                    total += if (currency.currencyCode == homeCurrency){
                        currency.balance
                    } else {
                        val multiplier = DummyContent.CurrenciesRate["$homeCurrency${currency.currencyCode}"]
                        currency.balance * multiplier!!
                    }
                }
            }
            return total
        }
        throw Exception("Home currency is not setup!")
    }

}
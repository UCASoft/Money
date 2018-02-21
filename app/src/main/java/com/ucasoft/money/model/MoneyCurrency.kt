package com.ucasoft.money.model

import java.util.*

class MoneyCurrency {

    var amount: Double = 0.0

    lateinit var currencyCode: String

    override fun toString(): String {

        if (Symbols.containsKey(currencyCode)){
            return "$amount " + Symbols[currencyCode]
        }
        return "$amount $currencyCode"
    }

    companion object {

        val Symbols: HashMap<String, String> = hashMapOf("RUB" to "₽", "CZK" to "Kč", "USD" to "$", "EUR" to "€", "THB" to "฿")

        fun newInstance(balance: Double, currencyCode: String) : MoneyCurrency {
            val currency = MoneyCurrency()
            currency.amount = balance
            currency.currencyCode = currencyCode
            return currency
        }
    }
}
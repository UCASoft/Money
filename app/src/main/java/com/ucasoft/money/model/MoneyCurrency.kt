package com.ucasoft.money.model

import java.util.*

class MoneyCurrency {

    var balance: Double = 0.0

    lateinit var currencyCode: String

    override fun toString(): String {

        if (Symbols.containsKey(currencyCode)){
            return "$balance " + Symbols[currencyCode]
        }
        return "$balance $currencyCode"
    }

    companion object {

        val Symbols: HashMap<String, String> = hashMapOf(
                "CZK" to "Kč",
                "EUR" to "€",
                "GNF" to "FG",
                "HRK" to "Kn",
                "HUF" to "ƒ",
                "LKR" to "₨",
                "MNT" to "₮",
                "PLN" to "zł",
                "RUB" to "₽",
                "THB" to "฿",
                "TRY" to "₺",
                "USD" to "$",
                "VND" to "₫")

        fun newInstance(balance: Double, currencyCode: String) : MoneyCurrency {
            val currency = MoneyCurrency()
            currency.balance = balance
            currency.currencyCode = currencyCode
            return currency
        }
    }
}
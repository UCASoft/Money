package com.ucasoft.money.model

import java.util.*

class MoneyCurrency(private val amount: Double, private val currencyCode: String) {

    override fun toString(): String {

        if (Symbols.containsKey(currencyCode)){
            return "$amount " + Symbols[currencyCode]
        }
        return "$amount $currencyCode"
    }

    companion object {
        val Symbols: HashMap<String, String> = hashMapOf("RUB" to "₽", "CZK" to "Kč", "USD" to "$", "EUR" to "€", "THB" to "฿")
    }
}
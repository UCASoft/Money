package com.ucasoft.money.model

import java.util.*

class MoneyCurrency(private val amount: Double, private val currencyCode: String) {

    private val symbols : HashMap<String, String> = hashMapOf("RUB" to "₽", "CZK" to "Kč", "USD" to "$", "EUR" to "€", "THB" to "฿")

    override fun toString(): String {

        if (symbols.containsKey(currencyCode)){
            return "$amount " + symbols[currencyCode]
        }
        return "$amount $currencyCode"
    }
}
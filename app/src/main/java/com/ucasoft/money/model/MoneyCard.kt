package com.ucasoft.money.model

class MoneyCard {

    var logoResource: Int = 0

    lateinit var number: String

    companion object {
        fun newInstance(logoResourceId: Int, number: String): MoneyCard{
            val card = MoneyCard()
            card.logoResource = logoResourceId
            card.number = number
            return card
        }
    }
}
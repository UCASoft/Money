package com.ucasoft.money.model

class Card {

    var logoResource: Int = 0

    lateinit var number: String

    companion object {
        fun newInstance(logoResourceId: Int, number: String): Card {
            val card = Card()
            card.logoResource = logoResourceId
            card.number = number
            return card
        }
    }
}
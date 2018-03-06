package com.ucasoft.money.model

class PaymentSystem {

    var logoResource: Int = 0

    lateinit var name: String

    companion object {
        fun newInstance(logoResourceId: Int, name: String): PaymentSystem {
            val system = PaymentSystem()
            system.logoResource = logoResourceId
            system.name = name
            return system
        }
    }
}
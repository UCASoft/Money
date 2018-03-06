package com.ucasoft.money.model

class Bank {

    lateinit var name: String

    companion object {
        fun newInstance(name: String): Bank {
            val bank = Bank()
            bank.name = name
            return bank
        }
    }
}
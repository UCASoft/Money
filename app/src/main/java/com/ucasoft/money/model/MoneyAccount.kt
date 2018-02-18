package com.ucasoft.money.model

import java.io.Serializable

open class MoneyAccount : Serializable {

    lateinit var name: String

    var logoResource: Int = 0

    lateinit var currencies: ArrayList<MoneyCurrency>

    companion object {

        fun newInstance(logoResource : Int, name : String, currencies: ArrayList<MoneyCurrency>) : MoneyAccount {
            val account = MoneyAccount()
            account.logoResource = logoResource
            account.name = name
            account.currencies = currencies
            return account
        }
    }
}
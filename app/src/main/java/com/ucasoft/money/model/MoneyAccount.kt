package com.ucasoft.money.model

open class MoneyAccount {

    lateinit var name: String

    var logoResource: Int = 0

    companion object {

        fun newInstance(logoResource : Int, name : String) : MoneyAccount {
            val account = MoneyAccount()
            account.logoResource = logoResource
            account.name = name
            return account
        }
    }
}
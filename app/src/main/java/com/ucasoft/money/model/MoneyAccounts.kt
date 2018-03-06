package com.ucasoft.money.model

import android.content.Context
import com.ucasoft.money.MoneyApplication
import com.ucasoft.money.contracts.IMoneyContent
import com.ucasoft.money.helpers.PreferencesHelper
import javax.inject.Inject

class MoneyAccounts(private val context: Context) : ArrayList<MoneyAccount>() {

    init {
        MoneyApplication.injector.inject(this)
    }

    @Inject
    lateinit var preferences: PreferencesHelper

    fun getTotal(): Double{
        val homeCurrency = homeCurrency()
        if (!homeCurrency.isEmpty()) {
            var total = 0.0
            for (i in 0 until this.size){
                val account = this[i]
                for (j in 0 until account.currencies.size){
                    val currency = account.currencies[j]
                    total += if (currency.currencyCode == homeCurrency){
                        currency.balance
                    } else {
                        val multiplier = dummyContent().currenciesRate["$homeCurrency${currency.currencyCode}"]
                        currency.balance * multiplier!!
                    }
                }
            }
            return total
        }
        throw Exception("Home currency is not setup!")
    }

    fun getCurrenciesForRates() : HashSet<String>{
        val homeCurrency = homeCurrency()
        if (!homeCurrency.isEmpty()){
            val result = HashSet<String>()
            for (i in 0 until this.size){
                val account = this[i]
                for (j in 0 until account.currencies.size){
                    val currency = account.currencies[j]
                    if (currency.currencyCode != homeCurrency){
                        result.add(currency.currencyCode)
                    }
                }
            }
            return result
        }
        throw Exception("Home currency is not setup!")
    }

    private fun homeCurrency() : String{
        return preferences.getHomeCurrency()
    }

    private fun dummyContent() : IMoneyContent {
        return (context as MoneyApplication).content
    }

}
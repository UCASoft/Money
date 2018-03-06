package com.ucasoft.money.adapters

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import com.ucasoft.money.MoneyApplication
import com.ucasoft.money.contracts.IMoneyContent
import com.ucasoft.money.helpers.PreferencesHelper
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject

class CurrencyRatesSyncAdapter(context: Context?, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

   init {
       MoneyApplication.injector.inject(this)
    }

    @Inject
    lateinit var content: IMoneyContent

    @Inject
    lateinit var preferences: PreferencesHelper

    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
        try {
            val homeCurrency = preferences.getHomeCurrency()
            if (!homeCurrency.isEmpty()) {
                content.currenciesRate.clear()
                val rates = content.accounts.getCurrenciesForRates().toList()
                for (i in 0 until rates.size) {
                    val currencyCode = rates[i]
                    val result = URL("https://freecurrencyrates.com/api/action.php?do=cvals&iso=$homeCurrency&f=$currencyCode&v=1").readText()
                    val jObject = JSONObject(result)
                    content.currenciesRate.set("$homeCurrency$currencyCode", jObject.getDouble(homeCurrency))
                }
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}
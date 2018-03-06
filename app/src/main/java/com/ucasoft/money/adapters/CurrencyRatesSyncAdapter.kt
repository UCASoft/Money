package com.ucasoft.money.adapters

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import com.ucasoft.money.dummy.DummyApplication
import com.ucasoft.money.helpers.PreferencesHelper
import org.json.JSONObject
import java.net.URL

class CurrencyRatesSyncAdapter(context: Context?, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

   init {

    }

    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
        try {
            val preferencesHelper = PreferencesHelper.getInstance(context)
            val homeCurrency = preferencesHelper.getHomeCurrency()
            if (!homeCurrency.isEmpty()) {
                val content = (context as DummyApplication).content
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
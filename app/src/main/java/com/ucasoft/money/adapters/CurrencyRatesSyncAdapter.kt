package com.ucasoft.money.adapters

import android.accounts.Account
import android.content.AbstractThreadedSyncAdapter
import android.content.ContentProviderClient
import android.content.Context
import android.content.SyncResult
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import org.json.JSONObject
import java.net.URL

class CurrencyRatesSyncAdapter(context: Context?, autoInitialize: Boolean) : AbstractThreadedSyncAdapter(context, autoInitialize) {

   init {

    }

    override fun onPerformSync(account: Account?, extras: Bundle?, authority: String?, provider: ContentProviderClient?, syncResult: SyncResult?) {
        try {
            val result = URL("https://freecurrencyrates.com/api/action.php?do=cvals&iso=CZK&f=THB&v=1").readText()
            val jObject = JSONObject(result)
            val handler = Handler(Looper.getMainLooper())
            handler.post {
                Toast.makeText(context, jObject.getString("CZK"), Toast.LENGTH_LONG).show()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}
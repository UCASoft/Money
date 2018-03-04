package com.ucasoft.money.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ucasoft.money.adapters.CurrencyRatesSyncAdapter

class CurrencyRatesSyncService : Service() {

    override fun onCreate() {
        super.onCreate()

        synchronized(lockObject){
            if (syncAdapter == null){
                syncAdapter = CurrencyRatesSyncAdapter(applicationContext, true)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return syncAdapter!!.syncAdapterBinder
    }

    companion object {
        var syncAdapter: CurrencyRatesSyncAdapter? = null
        val lockObject = Object()
    }
}
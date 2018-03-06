package com.ucasoft.money.dummy

import android.app.Application
import com.ucasoft.money.contracts.IMoneyContent

class DummyApplication : Application() {

    lateinit var content: IMoneyContent

    override fun onCreate() {
        super.onCreate()
        content = DummyContent(this)
    }
}
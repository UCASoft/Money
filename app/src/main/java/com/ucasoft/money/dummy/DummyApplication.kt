package com.ucasoft.money.dummy

import android.app.Application

class DummyApplication : Application() {

    lateinit var content: DummyContent

    override fun onCreate() {
        super.onCreate()
        content = DummyContent(this)
    }
}
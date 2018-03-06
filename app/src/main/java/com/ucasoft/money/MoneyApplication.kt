package com.ucasoft.money

import android.app.Application
import com.ucasoft.money.contracts.IMoneyContent
import com.ucasoft.money.dummy.ContentComponent
import com.ucasoft.money.dummy.ContentModule
import com.ucasoft.money.dummy.DaggerContentComponent
import javax.inject.Inject

class MoneyApplication : Application() {

    @Inject
    lateinit var content: IMoneyContent

    override fun onCreate() {
        super.onCreate()
        injector = DaggerContentComponent.builder().contentModule(ContentModule(this)).build()
        injector.inject(this)
    }

    companion object {
        @JvmStatic lateinit var injector: ContentComponent
    }
}
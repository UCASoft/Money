package com.ucasoft.money.dummy

import com.ucasoft.money.MoneyApplication
import com.ucasoft.money.adapters.CurrencyRatesSyncAdapter
import com.ucasoft.money.fragments.AccountFragment
import com.ucasoft.money.model.MoneyAccounts
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContentModule::class])
interface ContentComponent {

    fun inject(application: MoneyApplication)

    fun inject(fragment: AccountFragment)

    fun inject(adapter: CurrencyRatesSyncAdapter)

    fun inject(account: MoneyAccounts)
}
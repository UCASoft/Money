package com.ucasoft.money.contracts

import com.ucasoft.money.model.MoneyAccounts
import java.util.*

interface IMoneyContent {

    var accounts: MoneyAccounts

    val currenciesRate: HashMap<String, Double>
}
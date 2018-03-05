package com.ucasoft.money.dummy

import android.content.Context
import com.ucasoft.money.R
import com.ucasoft.money.contracts.IMoneyContent
import com.ucasoft.money.model.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
 class DummyContent(private var context: Context) : IMoneyContent {

    override lateinit var accounts: MoneyAccounts

    override val currenciesRate: HashMap<String, Double> = hashMapOf(
            "CZKRUB" to 0.362,
            "CZKUSD" to 20.637,
            "CZKEUR" to 25.384,
            "CZKTHB" to 0.656
    )

    init {
        val jsonDummies = loadDummyJson()
        loadAccounts(jsonDummies)
    }

    private fun loadAccounts(dummies: JSONObject) {
        accounts = MoneyAccounts(context)
        val accounts = dummies.getJSONArray("accounts")
        (0 until accounts.length()).mapTo(this.accounts){buildAccount(accounts.getJSONObject(it))}
    }

    private fun buildAccount(account: JSONObject) : MoneyAccount{
        val accountLogo = identifierByName(account.getString("logo"))
        val name = account.getString("name")
        val currencies = buildCurrencies(account.getJSONArray("currencies"))
        if (account.has("bank")){
            val bank = buildBank(account.getJSONObject("bank"))
            val cards = ArrayList<Card>()
            if (account.has("cards")) {
                val jsonCards = account.getJSONArray("cards")
                (0 until jsonCards.length()).mapTo(cards) { buildCard(jsonCards.getJSONObject(it)) }
            }
            return MoneyBankAccount.newInstance(accountLogo, name, currencies, bank, cards)
        }
        return MoneyAccount.newInstance(accountLogo, name, currencies)
    }

    private fun buildCurrencies(currencies: JSONArray): ArrayList<MoneyCurrency> {
        val result = ArrayList<MoneyCurrency>()
        (0 until currencies.length()).mapTo(result){buildCurrency(currencies.getJSONObject(it))}
        return result
    }

    private fun buildCurrency(currency: JSONObject): MoneyCurrency {
        val amount = currency.getDouble("amount")
        val currencyCode = currency.getString("currency")
        return MoneyCurrency.newInstance(amount, currencyCode)
    }

    private fun buildBank(jsonBank: JSONObject): Bank {
        val name = jsonBank.getString("name")
        return Bank.newInstance(name)
    }

    private fun buildCard(jsonCard: JSONObject): Card{
        val cardLogo = identifierByName(jsonCard.getString("logo"))
        val cardNumber = jsonCard.getString("number")
        return Card.newInstance(cardLogo, cardNumber)
    }

    private fun identifierByName(identifierName: String) =
            context.resources.getIdentifier(identifierName, "drawable", context.packageName)

    private fun loadDummyJson() : JSONObject {
        val jsonStream = context.resources.openRawResource(R.raw.secrets)
        val jsonObject = JSONObject(convertStreamToString(jsonStream))
        return jsonObject.getJSONObject("dummies")
    }

    private fun convertStreamToString(stream: InputStream): String {
        val s = Scanner(stream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
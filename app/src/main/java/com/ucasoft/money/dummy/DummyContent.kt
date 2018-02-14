package com.ucasoft.money.dummy

import android.content.Context
import com.ucasoft.money.R
import com.ucasoft.money.model.Bank
import com.ucasoft.money.model.Card
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount
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
 class DummyContent constructor(private var context: Context){

    lateinit var MoneyAccounts: MutableList<MoneyAccount>

    init {
        val jsonDummies = loadDummyJson()
        loadAccounts(jsonDummies)
    }

    private fun loadAccounts(dummies: JSONObject) {
        MoneyAccounts = ArrayList()
        val accounts = dummies.getJSONArray("accounts")
        (0 until accounts.length()).mapTo(MoneyAccounts){buildAccount(accounts.getJSONObject(it))}
    }

    private fun buildAccount(account: JSONObject) : MoneyAccount{
        val accountLogo = identifierByName(account.getString("logo"))
        val name = account.getString("name")
        if (account.has("bank")){
            val bank = buildBank(account.getJSONObject("bank"))
            var cards : ArrayList<Card>? = null
            if (account.has("cards")) {
                val jsonCards = account.getJSONArray("cards")
                cards = ArrayList<Card>(jsonCards.length())
                (0 until jsonCards.length()).mapTo(cards) { buildCard(jsonCards.getJSONObject(it)) }
            }
            return MoneyBankAccount.newInstance(accountLogo, name, bank, cards)
        }
        return MoneyAccount.newInstance(accountLogo, name)
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
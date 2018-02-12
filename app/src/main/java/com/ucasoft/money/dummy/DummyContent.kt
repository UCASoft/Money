package com.ucasoft.money.dummy

import android.content.Context
import com.ucasoft.money.R
import com.ucasoft.money.model.MoneyAccount
import com.ucasoft.money.model.MoneyBankAccount
import com.ucasoft.money.model.MoneyCard
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
 class DummyContent constructor(context: Context){

    /**
     * An array of sample (dummy) items.
     */
    lateinit var MoneyAccounts: MutableList<MoneyAccount>


    init {
        val jsonDummies = loadDummyJson(context)
        loadAccounts(context, jsonDummies)
    }

    private fun loadAccounts(context: Context, dummies: JSONObject) {
        MoneyAccounts = ArrayList()
        val accounts = dummies.getJSONArray("accounts")
        for (i in 0 until accounts.length()){
            val jsonAccount = accounts.getJSONObject(i)
            val accountLogo = identifierByName(context, jsonAccount.getString("logo"))
            val name = jsonAccount.getString("name")
            if (jsonAccount.has("cards")){
                val jsonCards = jsonAccount.getJSONArray("cards")
                val cards = ArrayList<MoneyCard>(jsonCards.length())
                for (j in 0 until jsonCards.length()){
                    val jsonCard = jsonCards.getJSONObject(j)
                    val cardLogo = identifierByName(context, jsonCard.getString("logo"))
                    val cardNumber = jsonCard.getString("number")
                    cards.add(MoneyCard.newInstance(cardLogo, cardNumber))
                }
                MoneyAccounts.add(MoneyBankAccount.newInstance(accountLogo, name, cards))
            } else {
                MoneyAccounts.add(MoneyAccount.newInstance(accountLogo, name))
            }
        }
    }

    private fun identifierByName(context: Context, identifierName: String) =
            context.resources.getIdentifier(identifierName, "drawable", context.packageName)

    private fun loadDummyJson(context: Context) : JSONObject {
        val jsonStream = context.resources.openRawResource(R.raw.secrets)
        val jsonObject = JSONObject(convertStreamToString(jsonStream))
        return jsonObject.getJSONObject("dummies")
    }

    private fun convertStreamToString(stream: InputStream): String {
        val s = Scanner(stream).useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }
}
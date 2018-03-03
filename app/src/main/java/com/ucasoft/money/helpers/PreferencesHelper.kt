package com.ucasoft.money.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferencesHelper private constructor(private var context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getHomeCurrency() : String {
        return preferences.getString(HomeCurrencyKey, "")
    }

    companion object {

        const val HomeCurrencyKey = "home_currency"

        // TODO Check Kotlin warning!
       @Volatile private var instance : PreferencesHelper? = null

        fun getInstance(context: Context): PreferencesHelper {
            if (instance?.context == context){
                return instance!!
            }

            return synchronized(this){
                instance = PreferencesHelper(context)
                instance!!
            }
        }
    }
}
package com.ucasoft.money.helpers

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class PreferencesHelper private constructor(context: Context) {

    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getHomeCurrency() : String {
        return preferences.getString(HomeCurrencyKey, "")
    }

    companion object {

        const val HomeCurrencyKey = "home_currency"

        fun getInstance(context: Context): PreferencesHelper {
            return PreferencesHelper(context)
        }
    }
}
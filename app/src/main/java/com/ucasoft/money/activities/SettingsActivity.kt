package com.ucasoft.money.activities

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.view.MenuItem
import com.ucasoft.money.R
import com.ucasoft.money.helpers.PreferencesHelper
import com.ucasoft.money.model.MoneyCurrency

class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
    }

    override fun onIsMultiPane(): Boolean {
        return isXLargeTablet(this)
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: MutableList<Header>?) {
        loadHeadersFromResource(R.xml.preference_headers, target)
    }

    override fun isValidFragment(fragmentName: String?): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || AccountsPreferences::class.java.name == fragmentName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class AccountsPreferences : PreferenceFragment(){

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.accounts_preferences)
            setHasOptionsMenu(true)

            val listPreference = findPreference(PreferencesHelper.HomeCurrencyKey) as ListPreference
            loadCurrencies(listPreference)
            bindPreferenceSummaryToValue(listPreference)
        }

        private fun loadCurrencies(preference: ListPreference) {
            val currencies = MoneyCurrency.Symbols.keys.toTypedArray().sortedArray()
            preference.entries = currencies
            preference.entryValues = currencies
        }
    }

    companion object {

        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->

            if(preference is ListPreference){
                val index = preference.findIndexOfValue(value.toString())
                preference.summary = if (index >= 0) preference.entries[index] else preference.summary
            }

            true
        }

        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getString(preference.key, ""))
        }
    }
}
package com.ucasoft.money.dummy

import android.content.Context
import com.ucasoft.money.contracts.IMoneyContent
import com.ucasoft.money.helpers.PreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContentModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContent() : IMoneyContent = DummyContent(context)

    @Provides
    @Singleton
    fun providePreferences() : PreferencesHelper = PreferencesHelper.getInstance(context)
}
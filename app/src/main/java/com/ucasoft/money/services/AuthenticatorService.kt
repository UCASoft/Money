package com.ucasoft.money.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.ucasoft.money.Authenticator

class AuthenticatorService : Service() {

    private lateinit var authenticator: Authenticator

    override fun onCreate() {
        authenticator = Authenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder {
        return authenticator.iBinder
    }
}
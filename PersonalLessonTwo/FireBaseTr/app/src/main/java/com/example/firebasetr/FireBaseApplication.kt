package com.example.firebasetr

import android.app.Application
import com.facebook.appevents.AppEventsLogger

class FireBaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppEventsLogger.activateApp(this)
    }
}
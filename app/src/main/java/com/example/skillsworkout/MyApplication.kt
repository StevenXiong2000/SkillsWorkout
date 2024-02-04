package com.example.skillsworkout

import android.app.Application
import com.example.skillsworkout.data.NotificationUtils

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
    }

    // You can override methods like onCreate() for initialization
    override fun onCreate() {
        super.onCreate()

        instance = this

        // Perform your application-wide initialization here
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        NotificationUtils.createNotificationChannel()
    }
}
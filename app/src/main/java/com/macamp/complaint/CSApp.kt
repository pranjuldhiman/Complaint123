package com.macamp.complaint

import android.app.Application
import android.content.Context
import com.macamp.complaint.utils.Preferences

class CSApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Preferences.initPreferences(this)
    }


    companion object {
        val Context.context: CSApp
            get() = applicationContext as CSApp

        lateinit var instance: CSApp
//        lateinit var singleton: Singleton
//            private set
    }

}
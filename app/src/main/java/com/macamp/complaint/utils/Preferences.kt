package com.macamp.complaint.utils

import android.content.Context

class Preferences {
    companion object {
        var prefs: EncryptedPreferences? = null
        fun initPreferences(context: Context) {
            prefs = EncryptedPreferences(context)
        }
    }
}
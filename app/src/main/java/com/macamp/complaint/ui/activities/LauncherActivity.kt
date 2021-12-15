package com.macamp.complaint.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.macamp.complaint.R
import com.macamp.complaint.utils.getUserInfo
import com.macamp.complaint.utils.startActivity

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_launcher)

        val user = getUserInfo()
        if (!user?.email.isNullOrEmpty()) {
            startActivity<MainActivity>()
            finishAffinity()
        }
    }
}
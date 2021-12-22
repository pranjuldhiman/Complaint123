package com.macamp.complaint.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.macamp.complaint.R
import com.macamp.complaint.utils.getUserInfo
import com.macamp.complaint.utils.startActivity

class LauncherActivity : AppCompatActivity() {
    companion object {
        var data: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_launcher)

        val user = getUserInfo()
        onNewIntent(intent);

        if (!user?.email.isNullOrEmpty()) {


            intent?.extras?.let {
                if (it.containsKey("showPending")) {
                    data = it.getString("showPending")
                }
            }
            if (data != null) {
                startActivity<MainActivity> {
                    putExtra("showPending", "1")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            } else {
                startActivity<MainActivity>()
            }
            data = null

            finishAffinity()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.extras?.let {
            if (it.containsKey("showPending")) {
                data = it.getString("showPending")
            }
        }
        if (data != null) {
            startActivity<MainActivity> {
                putExtra("showPending", "1")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        } else {
            startActivity<MainActivity>()
        }
        data = null
    }
}
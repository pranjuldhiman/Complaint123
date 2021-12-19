package com.macamp.complaint.data

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.macamp.complaint.R
import com.macamp.complaint.ui.activities.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        EventBus.getDefault().postSticky(Constants.NOTIFICATION_DATA)
        if (remoteMessage.notification != null) {
            Log.e("TAG", "onMessageReceived: ${remoteMessage.notification!!.title} " +
                    "${remoteMessage.notification!!.body}" )
            showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
        } else {
//            showNotification(remoteMessage.data["title"], remoteMessage.data["body"])
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(title: String?, message: String?) {
        val mChannelId = getString(R.string.default_notification_channel_id)
        val mImportance = NotificationManager.IMPORTANCE_HIGH
        val mChannelName = getString(R.string.app_name)
        val mIntent = Intent(this, MainActivity::class.java)
        mIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        mIntent.putExtra(Constants.NOTIFICATION_DATA, "1")

        val NOTIID = System.currentTimeMillis().toInt()
        mIntent.action = Intent.ACTION_MAIN
        mIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val pendingIntent = PendingIntent.getActivity(
            this, NOTIID /* SyncFriendsRequest code */, mIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
                    or PendingIntent.FLAG_ONE_SHOT
        )
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, mChannelId) //.setSmallIcon(getNotificationIcon())
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            notificationBuilder.setSmallIcon(R.drawable.ic_notification_icon)
            notificationBuilder.color =
                ResourcesCompat.getColor(resources, R.color.white, null)
        } else {
//            notificationBuilder.setSmallIcon(R.drawable.ic_notification_icon)
        }
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*
         **********************   push for android oreo version *************
         */if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                mChannelId, mChannelName, mImportance
            )
            notificationManager.createNotificationChannel(mChannel)
        }
        //handle notification manager notify may null issue
        notificationManager.notify(NOTIID /* ID of notification */, notificationBuilder.build())
    }

    fun generateNotificationId(): Int {
        return System.currentTimeMillis().toInt()
    }
}
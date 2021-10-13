package com.example.kontest.Receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kontest.MainActivity
import com.example.kontest.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val requestCode = intent!!.getIntExtra("requestCode",0)
        val sitesName = intent.getStringExtra("SitesName")

        val i = Intent(context,MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(context,requestCode,i,0)

        val builder = NotificationCompat.Builder(context!!,"Samkit5025")
            .setSmallIcon(R.drawable.klogoone)
            .setContentTitle("Kontest Remainder")
            .setContentText("Remainder for upcoming $sitesName contest")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123,builder.build())

        playRingtoneOnNotification(context)
    }

    private fun playRingtoneOnNotification(context: Context){
        try{
            val defaultRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val r = RingtoneManager.getRingtone(context,defaultRingtone)
            r.play()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

}
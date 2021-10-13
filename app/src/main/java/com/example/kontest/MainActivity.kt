package com.example.kontest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        createNotificationChannel()

        val navController = findNavController(R.id.fragmentContainerView)
        navController.addOnDestinationChangedListener { _, destination,_->
            if(destination.id == R.id.splashFragment){
                toolbar.visibility = View.GONE
            }
            else{
                toolbar.visibility = View.VISIBLE
            }
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Kontes Remainder Channel"
            val des = "Channel by Samkit"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel("Samkit5025",name,importance).apply {
                description = des
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}
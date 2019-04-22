package br.com.albertdanielricardo.foodcontrol.notification

import android.app.IntentService
import android.content.Intent
import android.util.Log

class NotificationService : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        val msg = intent?.getStringExtra("MSG")
        val msg2 = intent?.getStringExtra("MSG2")
        Log.i("HEIDER", msg)
        Log.i("HEIDER2", msg2)


    }

}
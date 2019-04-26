package br.com.albertdanielricardo.foodcontrol.notification

import android.app.IntentService
import android.content.Intent
import android.util.Log

class NotificationService : IntentService("") {
    override fun onHandleIntent(intent: Intent?) {
        val msg = intent?.getStringExtra("MSG")
        val msg2 = intent?.getStringExtra("MSG2")
        Log.i("FoodLog1", msg)
        Log.i("FoodLog2", msg2)


    }

}
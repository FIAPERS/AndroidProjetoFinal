package br.com.albertdanielricardo.foodcontrol

import android.util.Log
import br.com.albertdanielricardo.foodcontrol.notification.NotificationUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onNewToken(token: String?) {
        Log.e("FMS_TOKEN", token)
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        Log.d("NOTIFICACAO_TITLE", "Message Notification Title: " +
                p0?.notification?.title)

        Log.d("NOTIFICACAO_BODY", "Message Notification Body: " +
                p0?.notification?.body)

        NotificationUtils.showNotification(this, 1234, "PUSH", "PUSH",
            p0?.notification?.title!!, p0?.notification?.body!!)


    }
}
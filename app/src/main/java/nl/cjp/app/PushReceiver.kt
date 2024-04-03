package nl.cjp.app

import android.util.Log
import com.cm.cmpush.CMPush
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushReceiver : FirebaseMessagingService() {
    companion object {
        const val TAG = "PushReceiver"
    }

    override fun onNewToken(token: String) {
        CMPush.updateToken(
            context = applicationContext,
            pushToken = token,
            callback = { success, error, _ ->
                if (success) {
                    Log.d(TAG, "Successfully updated token!")
                } else {
                    Log.e(TAG, "Failed to update token: $error")
                }
            }
        )
    }

    /**
     * Incoming push messages will arrive here. They aren't being shown to the user yet.
     * The CMPush library will notify CM the message has been received and create the Notification
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Notify CMPush library that a push message has been received
        CMPush.pushReceived(
            context = applicationContext,
            data = remoteMessage.data,
            callback = { success, error ->
                Log.d(TAG, "Confirmed push: $success, $error")
            }
        )

        super.onMessageReceived(remoteMessage)
    }
}
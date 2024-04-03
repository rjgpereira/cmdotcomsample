package nl.cjp.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.cm.cmpush.CMPush
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
//import com.google.firebase.messaging.FirebaseMessaging

import nl.cjp.app.ui.theme.CMdotComSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMdotComSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("Android")
                }
            }
        }

        val startIntent = Intent(applicationContext, MainActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        CMPush.initialize(
            context = this,
            applicationKey = "API_KEY",
            channelId = "CMPushAnnouncements",
            channelName = "Announcements",
            channelDescription = "Announcements of our latest products!",
            notificationIcon = R.drawable.ic_launcher_foreground,
            notificationIntent = startIntent
        )

        FirebaseApp.initializeApp(this)
//        Log.d("HEY", FirebaseMessaging.getInstance().token.toString())
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result ?: ""
            CMPush.updateToken(
                context = this@MainActivity,
                pushToken = token,
                callback = { success, error, installationId ->
                    if (success) {
                        Log.d("CM.com", installationId.toString())
                    } else {
                        // Handle error
                    }
                }
            )
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CMdotComSampleTheme {
        Greeting("Android")
    }
}
    package nl.cjp.app

    import android.app.Application
    import android.content.Intent
    import com.cm.cmpush.CMPush

    class CoreApp: Application() {

        override fun onCreate() {
            super.onCreate()

            val startIntent = Intent(this, MainActivity::class.java)
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
        }
    }
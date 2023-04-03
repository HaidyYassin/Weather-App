package eg.iti.sv.weather.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import eg.iti.sv.weather.R
import eg.iti.sv.weather.home.view.HomeFragment


const val CHANNEL_ID = "19"
class NotificationHelper(var context: Context) {

    fun createNotification(title:String,content:String){
        createNotificationChannel()
        val intent= Intent(context, HomeFragment::class.java)
        val pendingIntent= PendingIntent.getActivity(context,0,intent,0)

        var builder: NotificationCompat.Builder=
            NotificationCompat.Builder(context,CHANNEL_ID)
            .setSmallIcon(R.drawable.weather)
            .setContentTitle(title)
            .setContentText(content)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManagerCompat= NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(10,builder.build())
    }


     private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Display Weather",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My Channel Weather"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
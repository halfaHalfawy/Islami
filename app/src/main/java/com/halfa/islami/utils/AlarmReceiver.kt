package com.halfa.islami.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.halfa.islami.R
import com.halfa.islami.utils.service.AlarmSoundService

class AlarmReceiver : BroadcastReceiver() {
    val work = OneTimeWorkRequestBuilder<PlayAudioWorker>()
        .setInputData(workDataOf("audio_file" to R.raw.azan))
//        .set
        .build()

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "STOP_AUDIO") {
         /*   val stopAudioIntent = Intent(context, AlarmSoundService::class.java)
            context.stopService(stopAudioIntent)*/
//            WorkManager.getInstance(context)
//            WorkManager.getInstance(context).cancelUniqueWork(work)

//                .cancelWorkById(work.id)

        } else {
            runNotification(context)
            runAudioService(context, intent)
        }


    }

    private fun runAudioService(context: Context, intent: Intent) {
      /*  val startAudioIntent = Intent(context, AlarmSoundService::class.java)
        startAudioIntent.putExtra("audio_file", R.raw.azan)
        context.startService(startAudioIntent)*/


        WorkManager.getInstance(context).enqueue(work)


    }


    private fun runNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Alarm Channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val stopAudioIntent = Intent(context, AlarmReceiver::class.java)
        stopAudioIntent.action = "STOP_AUDIO"
        val stopAudioPendingIntent = PendingIntent.getBroadcast(context, 0, stopAudioIntent, 0)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Alarm")
            .setContentText("Your alarm has gone off.")
            .addAction(R.drawable.ic_stop, "Stop", stopAudioPendingIntent)

            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}

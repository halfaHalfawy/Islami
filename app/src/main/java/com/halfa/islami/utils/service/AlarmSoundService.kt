package com.halfa.islami.utils.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.halfa.islami.R

class AlarmSoundService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    fun getMediaPlayer(context: Context, id: Int): MediaPlayer {

        if (!::mediaPlayer.isInitialized) mediaPlayer = MediaPlayer.create(this, R.raw.azan)
        return mediaPlayer
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer = getMediaPlayer(this, R.raw.azan)

        if (intent?.action == "STOP_AUDIO") {
            mediaPlayer.stop()
            mediaPlayer.release()
            stopForeground(true)
            stopSelf()
        } else {
            mediaPlayer.start()
            startForeground(1, createNotification())
        }

        return Service.START_STICKY

    }

    private fun createNotification(): Notification {


        val stopAudioIntent = Intent(this, AlarmSoundService::class.java)
        stopAudioIntent.action = "STOP_AUDIO"
        val stopAudioPendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getService(
                this, 0, stopAudioIntent, PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getService(
                this, 0, stopAudioIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Alarm Channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }


        val notificationBuilder =
            NotificationCompat.Builder(this, channelId).setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Prayer time ").setContentText("time for prayer")
                .setAutoCancel(true).setSilent(true).setContentIntent(stopAudioPendingIntent)
                .addAction(R.drawable.ic_stop, "Pray for prophet mohammed", stopAudioPendingIntent)
        return notificationBuilder.build()
    }


    /* override fun onDestroy() {
         super.onDestroy()
         mediaPlayer?.stop()
         mediaPlayer?.release()
         stopForeground(true)

     }*/

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}

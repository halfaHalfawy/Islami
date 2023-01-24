package com.halfa.islami.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.halfa.islami.R
import com.halfa.islami.utils.service.AlarmSoundService

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var startAudioIntent: Intent

    fun getStartIntent(context: Context): Intent {
        if (!::startAudioIntent.isInitialized)
            startAudioIntent = Intent(context, AlarmSoundService::class.java)

        return startAudioIntent
    }

    override fun onReceive(context: Context, intent: Intent) {
        startAudioIntent = getStartIntent(context)
        if (intent.action == "STOP_AUDIO") {
            context.stopService(getStartIntent(context))
//                .cancelWorkById(work.id)
        } else {
//            runNotification(context)
            runAudioService(context, intent)
        }


    }


    private fun runAudioService(context: Context, intent: Intent) {

        getStartIntent(context).putExtra("audio_file", R.raw.azan)
        context.startService(getStartIntent(context))
    }


}

package com.halfa.islami.utils.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.halfa.islami.R

class AlarmSoundService : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            mediaPlayer = MediaPlayer.create(
                this,
                R.raw.azan
//                (intent.extras?.get("audio_file") as Int)

            )
        }
        mediaPlayer?.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }


}

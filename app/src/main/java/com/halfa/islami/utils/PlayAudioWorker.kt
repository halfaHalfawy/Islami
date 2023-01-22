package com.halfa.islami.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.halfa.islami.R

class PlayAudioWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val audioFile = inputData.getInt("audio_file", R.raw.azan)
        val mediaPlayer = MediaPlayer.create(applicationContext, audioFile)
        mediaPlayer.start()
        return Result.success()
    }
}

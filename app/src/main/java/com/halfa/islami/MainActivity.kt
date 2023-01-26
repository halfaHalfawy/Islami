package com.halfa.islami

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.halfa.islami.repos.PrayerTimesRepository
import com.halfa.islami.utils.Constants
import com.halfa.islami.utils.SharedPreferenceHelper
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferenceHelper.init(this)
        var calendar = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val current = formatter.format(calendar)

        val lastSet = SharedPreferenceHelper.getString(Constants.LAST_SET_ALARMS_DATE)



    }

}
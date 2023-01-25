package com.halfa.islami

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.halfa.islami.repos.PrayerTimesRepository
import com.halfa.islami.utils.SharedPreferenceHelper
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferenceHelper.init(this)
        val currentDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("DD-MM-yyyy")
        val dateString = formatter.format(currentDate)


      /*  PrayerTimesRepository.prayerCalenderResponseMutableLiveData.observe(
            this,
        ) {

            val ss = it.data.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1)
            Log.d(TAG, "onCreate: $ss")
        }
        PrayerTimesRepository.getPrayerCalender(31.183847, 29.997063, fun(_, _) {

        })*/
    }
}
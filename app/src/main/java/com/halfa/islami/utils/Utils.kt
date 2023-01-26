package com.halfa.islami.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.halfa.islami.models.Alarms
import com.halfa.islami.models.PrayerTimingsData
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun inflateLayout(layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }


    fun runAlarm(hour: Int, minute: Int, second: Int, activity: Activity, requestCode: Int) {

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(activity, requestCode, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(
                activity,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        }


        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    fun cancelAlarm(activity: Activity, requestCode: Int) {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(activity, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)

//         intent = intent.setClassName("com.halfa.islami.utils", "com.halfa.islami.utils.AlarmReceiver")
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(activity, requestCode, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(
                activity,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        }

        alarmManager.cancel(pendingIntent)

    }

    fun getNextPrayer(
        fajr: String, dhuhr: String,
        asr: String, maghrib: String, isha: String,
    ): Pair<String, Date> {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        val stf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val current = Calendar.getInstance(
            Locale.getDefault()
        ).time


        val currentTime = stf.format(current)
        val fajrTime = sdf.parse(fajr)
        val dhuhrTime = sdf.parse(dhuhr)
        val asrTime = sdf.parse(asr)
        val maghribTime = sdf.parse(maghrib)
        val ishaTime = sdf.parse(isha)

        val prayerList = listOf(fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime)
        var nextPrayer: String = "Fajr"
        var waitingTime: Long = Long.MAX_VALUE
        for (prayer in prayerList) {
            val diff = prayer.time - stf.parse(currentTime).time
            if (diff > 0) {

                if (diff < waitingTime) {
                    waitingTime = diff
                    nextPrayer = when (prayer) {
                        fajrTime -> "Fajr"
                        dhuhrTime -> "Dhuhr"
                        asrTime -> "Asr"
                        maghribTime -> "Maghrib"
                        ishaTime -> "Isha"
                        else -> "Fajr"
                    }
                }
            }
        }
        // here the waiting time is incresed by 2 so the incres is before this,the icreased
        // 2 hours is from the format func
        val sfWithout = SimpleDateFormat("HH:mm:ss")
        val timeUntilNextPrayer = stf.format(Date(waitingTime))
        return Pair(nextPrayer, Date(waitingTime))
    }

    fun getProperties(obj: Any): List<Map<String, String>> {
        val maps: MutableList<Map<String, String>> = mutableListOf()

        val fields = obj.javaClass.getDeclaredFields()
        for (field in fields) {
            val key = field.name
            field.isAccessible = true
            val value = field.get(obj).toString()

            maps.add(mapOf(key to value))
        }
        return maps
    }

    fun checkAndRunAlarms(timingsData: PrayerTimingsData, activity: Activity) {
        val thisDay = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val formatted = formatter.format(thisDay)


        val lastAlarms: Alarms? =
            SharedPreferenceHelper.getObject(
                Constants.LAST_SET_ALARMS,
                Alarms::class.java
            ) as Alarms?

        val runningAlarms: Alarms? =
            SharedPreferenceHelper.getObject(
                Constants.RUNNING_ALARMS,
                Alarms::class.java
            ) as Alarms?

        if (lastAlarms == null || runningAlarms == null) {
            SharedPreferenceHelper.putObject(
                Constants.LAST_SET_ALARMS,
                Alarms(prayerTimingsData = timingsData, day = formatted)
            )

            runAlarms(Alarms(prayerTimingsData = timingsData, day = formatted), activity)
        } else
            if (
                lastAlarms == runningAlarms
            ) {
                runAlarms(lastAlarms, activity)
            }

    }

    private fun runAlarms(alarms: Alarms, activity: Activity) {

        SharedPreferenceHelper.putObject(Constants.RUNNING_ALARMS, alarms)
        val listo: MutableList<Pair<Int, Int>> = mutableListOf<Pair<Int, Int>>()

        alarms.apply {
            if (fajr)
                listo.add(parseTime(prayerTimingsData.timings.fajr))
            if (dhuhr)
                listo.add(parseTime(prayerTimingsData.timings.dhuhr))
            if (asr)
                listo.add(parseTime(prayerTimingsData.timings.asr))
            if (maghrib)
                listo.add(parseTime(prayerTimingsData.timings.maghrib))
            if (isha)
                listo.add(parseTime(prayerTimingsData.timings.isha))
        }

        var i = 0
        listo.forEach {
            runAlarm(it.first, it.second, 0, activity, i)
            i++
        }
    }

    fun parseTime(timeString: String): Pair<Int, Int> {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = timeFormat.parse(timeString)
        val calendar = Calendar.getInstance()
        calendar.time = time
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }


    fun getAllPendingAlarms(activity: Activity): AlarmManager.AlarmClockInfo? {
        val alarmManager: AlarmManager =
            activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntents = alarmManager.nextAlarmClock
        return alarmIntents

    }
}
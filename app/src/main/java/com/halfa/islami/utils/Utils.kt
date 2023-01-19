package com.halfa.islami.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun inflateLayout(layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    fun getNextPrayer(
        fajr: String, dhuhr: String,
        asr: String, maghrib: String, isha: String,
    ): Pair<String, Date> {

        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        val stf = SimpleDateFormat("HH:mm:ss")

        val current = Calendar.getInstance().time
        val currentTime = stf.format(current)
        val fajrTime = sdf.parse(fajr)
        val dhuhrTime = sdf.parse(dhuhr)
        val asrTime = sdf.parse(asr)
        val maghribTime = sdf.parse(maghrib)
        val ishaTime = sdf.parse(isha)

        val prayerList = listOf(fajrTime, dhuhrTime, asrTime, maghribTime, ishaTime)
        var nextPrayer: String = ""
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
                        else -> ""
                    }
                }
            }
        }
//        val timeUntilNextPrayer = stf.format(Date(waitingTime))
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


}
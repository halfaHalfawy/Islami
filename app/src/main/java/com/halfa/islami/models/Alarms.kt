package com.halfa.islami.models

data class Alarms(
    var fajr: Boolean = true,
    var dhuhr: Boolean = true,
    var asr: Boolean = true,
    var maghrib: Boolean = true,
    var isha: Boolean = true,
    var day: String,
    var prayerTimingsData: PrayerTimingsData
)
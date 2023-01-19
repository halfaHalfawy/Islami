package com.halfa.islami.models

import com.google.gson.annotations.SerializedName

data class PrayerCalenderResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: List<PrayerTimingsData>
    )

data class PrayerTimesResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: PrayerTimingsData
)


data class PrayerTimingsData(
    @SerializedName("timings") val timings: Timings,
    @SerializedName("date") val date: Date,
    @SerializedName("meta") val meta: Meta
)

data class Timings(
    @SerializedName ("Fajr") val fajr: String,
    @SerializedName("Sunrise") val sunrise: String,
    @SerializedName("Dhuhr") val dhuhr: String,
    @SerializedName("Asr") val asr: String,
    @SerializedName("Sunset") val sunset: String,
    @SerializedName("Maghrib") val maghrib: String,
    @SerializedName("Isha") val isha: String,
    @SerializedName("Imsak") val imsak: String,
    @SerializedName("Midnight") val midnight: String,
    @SerializedName("Firstthird") val firstthird: String,
    @SerializedName("Lastthird") val lastthird: String
)

data class Date(
    @SerializedName("readable") val readable: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("gregorian") val gregorian: Gregorian,
    @SerializedName("hijri") val hijri: Hijri
)

data class Gregorian(
    @SerializedName("date") val date: String,
    @SerializedName("format") val format: String,
    @SerializedName("day") val day: String,
    @SerializedName("weekday") val weekday: Weekday,
    @SerializedName("month") val month: Month,
    @SerializedName("year") val year: String,
    @SerializedName("designation") val designation: Designation
)

data class Month(
    @SerializedName("number") val number: Int,
    @SerializedName("en") val en: String,
    @SerializedName("ar") val ar: String
)

data class Designation(
    @SerializedName("abbreviated") val abbreviated: String,
    @SerializedName("expanded") val expanded: String
)

data class Hijri(
    @SerializedName("date") val date: String,
    @SerializedName("format") val format: String,
    @SerializedName("day") val day: String,
    @SerializedName("weekday") val weekday: Weekday,
    @SerializedName("month") val month: Month,
    @SerializedName("year") val year: String,
    @SerializedName("designation") val designation: Designation,
    @SerializedName("holidays") val holidays: List<Any>

)

data class Meta(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("method") val method: Method,
    @SerializedName("latitudeAdjustmentMethod") val latitudeAdjustmentMethod: String,
    @SerializedName("midnightMode") val midnightMode: String,
    @SerializedName("school") val school: String,
    @SerializedName("offset") val offset: Offset
)

data class Method(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("params") val params: Params,
    @SerializedName("location") val location: Location
)

data class Params(
    @SerializedName("Fajr") val fajr: Double,
    @SerializedName("Isha") val isha: Double
)

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)

data class Offset(
    @SerializedName("Imsak") val imsak: Int,
    @SerializedName("Fajr") val fajr: Int,
    @SerializedName("Sunrise") val sunrise: Int,
    @SerializedName("Dhuhr") val dhuhr: Int,
    @SerializedName("Asr") val asr: Int,
    @SerializedName("Maghrib") val maghrib: Int,
    @SerializedName("Sunset") val sunset: Int,
    @SerializedName("Isha") val isha: Int,
    @SerializedName("Midnight") val midnight: Int
)

data class Weekday(
    @SerializedName("en") val en: String,
    @SerializedName("ar") val ar: String
)
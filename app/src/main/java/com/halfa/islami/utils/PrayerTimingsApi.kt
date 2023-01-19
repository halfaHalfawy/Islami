package com.halfa.islami.utils

import com.halfa.islami.models.PrayerTimesResponse
import com.halfa.islami.models.PrayerCalenderResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimingsApi {
    @GET("calendar")
    fun getPrayerTimings(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("date") date: String,
        @Query("method") method: Int
    ): Call<PrayerCalenderResponse>

    @GET("timings")
    fun getPrayerTime(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("date") date: String,
//        @Query("method") method: Int
    ): Call<PrayerTimesResponse>



}
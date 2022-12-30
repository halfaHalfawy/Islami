package com.halfa.islami

import com.google.gson.JsonObject
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
    ): Call<PrayerTimingsResponse>

    @GET("timings")
    fun getPrayerTime(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("date") date: String,
        @Query("method") method: Int
    ): Call<PrayerTimesResponse>



}
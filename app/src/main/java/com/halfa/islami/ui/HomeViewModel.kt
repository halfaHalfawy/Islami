package com.halfa.islami.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halfa.islami.PrayerTimesResponse
import com.halfa.islami.PrayerTimingsData
import com.halfa.islami.utils.PrayerTimingsApi
import com.halfa.islami.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel() : ViewModel() {

    var prayersTimes: MutableLiveData<List<Map<String, String>>> = MutableLiveData()
    var prayerTimesResponse: MutableLiveData<PrayerTimingsData> = MutableLiveData()

    init {
        prayersTimes.value = ArrayList()

    }


    fun getPrayers(longi: Double, lati: Double, context: Context) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val prayerTimingsApi = retrofit.create(PrayerTimingsApi::class.java)
        val call = prayerTimingsApi.getPrayerTime(
//            29.997063,
//            31.183847,
            lati, longi,
            "2022-12-31", 5
        )

        call.enqueue(object : Callback<PrayerTimesResponse?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<PrayerTimesResponse?>,
                response: Response<PrayerTimesResponse?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
//                    Log.d(HomeFragment.TAG, "onResponse: $data")
                    if (data != null) {

                        prayersTimes.value = Utils.getProperties(data.timings)
                        prayerTimesResponse.value = data!!

                    }

                }
//                Log.d(HomeFragment.TAG, "onResponse: $response")

            }

            override fun onFailure(call: Call<PrayerTimesResponse?>, t: Throwable) {

            }
        })
    }

}
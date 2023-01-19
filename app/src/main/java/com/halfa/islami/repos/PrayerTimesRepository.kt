package com.halfa.islami.repos

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.halfa.islami.models.PrayerTimesResponse
import com.halfa.islami.models.PrayerTimingsData
import com.halfa.islami.utils.PrayerTimingsApi
import com.halfa.islami.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object PrayerTimesRepository {

    var prayersTimes: MutableLiveData<List<Map<String, String>>> = MutableLiveData()
    var prayerTimingsDataMutableLiveData: MutableLiveData<PrayerTimingsData> = MutableLiveData()

    var prayerTimesResponseMutableLiveData: MutableLiveData<PrayerTimesResponse> = MutableLiveData()

    init {
        prayersTimes.value = ArrayList()
    }

    fun getPrayerTimingsMutable(): MutableLiveData<List<Map<String, String>>> {
        return prayersTimes
    }

    fun getPrayerTimesResponseMutable(): MutableLiveData<PrayerTimesResponse> {
        return prayerTimesResponseMutableLiveData
    }

    fun getPrayers(
        longi: Double,
        lati: Double,
        successCallBack: (isSuccess: Boolean, message: String) -> Unit
    ) {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val prayerTimingsApi = retrofit.create(PrayerTimingsApi::class.java)
        val currentDate = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val dateString = formatter.format(currentDate)


        val call = prayerTimingsApi.getPrayerTime(
//            29.997063,
//            31.183847,
            lati, longi,
            dateString
        )

        call.enqueue(object : Callback<PrayerTimesResponse?> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<PrayerTimesResponse?>,
                response: Response<PrayerTimesResponse?>
            ) {
                if (response.isSuccessful) {
                    prayerTimesResponseMutableLiveData.value = response.body()
                    val data = response.body()?.data
//                    Log.d(HomeFragment.TAG, "onResponse: $data")
                    if (data != null) {
                        successCallBack(true, "success")
                        prayersTimes.value = Utils.getProperties(data.timings)
                        prayerTimingsDataMutableLiveData.value = data!!

                    }

                }
//                Log.d(HomeFragment.TAG, "onResponse: $response")

            }

            override fun onFailure(call: Call<PrayerTimesResponse?>, t: Throwable) {
                t.printStackTrace()
                successCallBack(false, t.message.toString())

            }
        })
    }

}
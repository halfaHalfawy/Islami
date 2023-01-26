package com.halfa.islami.repos

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.halfa.islami.MainActivity
import com.halfa.islami.models.PrayerCalenderResponse
import com.halfa.islami.models.PrayerTimesResponse
import com.halfa.islami.models.PrayerTimingsData
import com.halfa.islami.utils.Constants
import com.halfa.islami.utils.PrayerTimingsApi
import com.halfa.islami.utils.SharedPreferenceHelper
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
    val prayerCalenderResponseMutableLiveData: MutableLiveData<PrayerCalenderResponse> =
        MutableLiveData()
   val prayerTimingsDataMutableLiveData: MutableLiveData<PrayerTimingsData> = MutableLiveData()

    var prayerTimesResponseMutableLiveData: MutableLiveData<PrayerTimesResponse> = MutableLiveData()





    fun getPrayerTimesResponseMutable(): MutableLiveData<PrayerTimesResponse> {
        return prayerTimesResponseMutableLiveData
    }

    fun setTodayTimings(prayerCalenderResponse: PrayerCalenderResponse?) {
        var tod = Calendar.getInstance()
        if (prayerCalenderResponse != null) {
            val timingsData = prayerCalenderResponse.data[tod.get(Calendar.DAY_OF_MONTH) - 1]
            val monthnumber = tod.get(Calendar.MONTH) + 1
            if (timingsData.date.gregorian.month.number == monthnumber) {
                // this mean that this data is for this month
//                so update the prayertimestiming
               prayerTimingsDataMutableLiveData.value = timingsData

            } else {
                // update the local database with the data of this month
                updateCalenderData(timingsData.meta.longitude, timingsData.meta.latitude)
            }

        }
    }

    private fun updateCalenderData(longitude: Double, latitude: Double) {
        SharedPreferenceHelper.deleteString(Constants.PRAYER_CALENDER_RESPONSE)

        getPrayerCalender(longitude, latitude, fun(b, m) {

        })
    }


    /*  fun getPrayerTimings(
          longi: Double, lati: Double, successCallBack: (isSuccess: Boolean, message: String) -> Unit
      ) {

          var prayerLocal: PrayerTimesResponse? = SharedPreferenceHelper.getObject(
              Constants.PRAYER_TIMES_RESPONSE, PrayerTimesResponse::class.java
          ) as PrayerTimesResponse?
          if (prayerLocal != null) {
              prayerTimesResponseMutableLiveData.value = prayerLocal!!
              successCallBack(true, "success")
              prayersTimes.value = Utils.getProperties(prayerLocal.data.timings)
              prayerTimingsDataMutableLiveData.value = prayerLocal.data!!
          } else {
              val retrofit = Retrofit.Builder().baseUrl("https://api.aladhan.com/v1/")
                  .addConverterFactory(GsonConverterFactory.create()).build()

              val prayerTimingsApi = retrofit.create(PrayerTimingsApi::class.java)
              val currentDate = Calendar.getInstance().time
              val formatter = SimpleDateFormat("yyyy-MM-dd")
              val dateString = formatter.format(currentDate)


              val call = prayerTimingsApi.getPrayerTime(
  //            29.997063,
  //            31.183847,
                  lati, longi, dateString
              )

              call.enqueue(object : Callback<PrayerTimesResponse?> {

                  @SuppressLint("SetTextI18n")
                  override fun onResponse(
                      call: Call<PrayerTimesResponse?>, response: Response<PrayerTimesResponse?>
                  ) {
                      if (response.isSuccessful) {
                          prayerTimesResponseMutableLiveData.value = response.body()
                          val data = response.body()?.data
  //                    Log.d(HomeFragment.TAG, "onResponse: $data")
                          if (data != null) {

                              SharedPreferenceHelper.putObject(
                                  Constants.PRAYER_TIMES_RESPONSE,
                                  response.body()!!
                              )
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
  */
    fun getPrayerCalender(
        longi: Double, lati: Double, successCallBack: (isSuccess: Boolean, message: String) -> Unit
    ) {

        var prayerCalenderLocal: PrayerCalenderResponse? = SharedPreferenceHelper.getObject(
            Constants.PRAYER_CALENDER_RESPONSE, PrayerCalenderResponse::class.java
        ) as PrayerCalenderResponse?



        if (prayerCalenderLocal != null) {

            prayerCalenderResponseMutableLiveData.value = prayerCalenderLocal!!
            setTodayTimings(prayerCalenderLocal)


        } else {

            val retrofit = Retrofit.Builder().baseUrl("https://api.aladhan.com/v1/")
                .addConverterFactory(GsonConverterFactory.create()).build()

            val prayerTimingsApi = retrofit.create(PrayerTimingsApi::class.java)
            val currentDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val dateString = formatter.format(currentDate)


            val call = prayerTimingsApi.getPrayerCalender(
//            29.997063,
//            31.183847,
                lati, longi
            )

            call.enqueue(object : Callback<PrayerCalenderResponse?> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<PrayerCalenderResponse?>, response: Response<PrayerCalenderResponse?>
                ) {
                    if (response.isSuccessful) {
                        prayerCalenderResponseMutableLiveData.value = response.body()
                        val data = response.body()?.data
                        successCallBack(true, "sucess")
//                    Log.d(HomeFragment.TAG, "onResponse: $data")
                        if (data != null) {

                            SharedPreferenceHelper.putObject(
                                Constants.PRAYER_CALENDER_RESPONSE,
                                response.body()!!
                            )

                        }
                        setTodayTimings(response.body())

                    }
//                Log.d(HomeFragment.TAG, "onResponse: $response")

                }

                override fun onFailure(call: Call<PrayerCalenderResponse?>, t: Throwable) {
                    t.printStackTrace()
                    successCallBack(false, t.message.toString())

                }
            })
        }
    }
}
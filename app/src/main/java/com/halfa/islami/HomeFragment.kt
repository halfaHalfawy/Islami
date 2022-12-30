package com.halfa.islami

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.halfa.islami.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding =
            FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))



        getPrayers()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getPrayers() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.aladhan.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())

            .build()

        val prayerTimingsApi = retrofit.create(PrayerTimingsApi::class.java)
        val call = prayerTimingsApi.getPrayerTime(29.997063,
            31.183847, "2022-12-31", 5)

       /* call.enqueue(object : Callback<PrayerTimingsResponse> {
            override fun onResponse(
                call: Call<PrayerTimingsResponse>,
                response: Response<PrayerTimingsResponse>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()?.data
                    Log.d(TAG, "onResponse: $data")
                    if (data != null) {
                      *//*  if (data.code in 200..399) {
//                            Snackbar.make(view!!, )

                            Log.d(Companion.TAG, "onResponse: "+data.status)
                        }*//*
                    }

                }
                // handle success
            }

            override fun onFailure(call: Call<PrayerTimingsResponse>, t: Throwable) {
                // handle failure
            }
        })
*/
  call.enqueue(object : Callback<PrayerTimesResponse?> {
      override fun onResponse(
          call: Call<PrayerTimesResponse?>,
          response: Response<PrayerTimesResponse?>
      ) {
          if (response.isSuccessful) {
              val data = response.body()?.data
              Log.d(TAG, "onResponse: $data")
              if (data != null) {

          }

      }
          Log.d(TAG, "onResponse: $response")

      }

      override fun onFailure(call: Call<PrayerTimesResponse?>, t: Throwable) {

      }
  })
    }

    companion object {
        private const val TAG = "HomeFragment"
    }


}







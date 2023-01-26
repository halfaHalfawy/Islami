package com.halfa.islami.ui

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halfa.islami.MainActivity
import com.halfa.islami.models.PrayerCalenderResponse
import com.halfa.islami.models.PrayerTimesResponse
import com.halfa.islami.models.PrayerTimingsData
import com.halfa.islami.repos.PrayerTimesRepository

class HomeViewModel() : ViewModel() {

    init {

        getPrayersTimesCalender().observe(MainActivity()) {

        }
    }

    fun getPrayerTimings(): MutableLiveData<PrayerTimingsData> {
        return PrayerTimesRepository.prayerTimingsDataMutableLiveData
    }

    fun getPrayersTimesCalender(): MutableLiveData<PrayerCalenderResponse> {
        return PrayerTimesRepository.prayerCalenderResponseMutableLiveData
    }


    fun getPrayerTimesMutable(): MutableLiveData<PrayerTimesResponse> {
        return PrayerTimesRepository.getPrayerTimesResponseMutable()
    }

    fun getPrayers(
        longi: Double,
        lati: Double,
        view: View,
        successCallBack: (isSuccess: Boolean, messages: String) -> Unit
    ) {

        if (PrayerTimesRepository.prayerCalenderResponseMutableLiveData.value == null || PrayerTimesRepository.prayerCalenderResponseMutableLiveData.value!!.data.isEmpty()) {
            PrayerTimesRepository.getPrayerCalender(
                longi,
                lati,
                successCallBack
            )
        } else {
            PrayerTimesRepository.prayerTimingsDataMutableLiveData.value = PrayerTimesRepository.prayerTimingsDataMutableLiveData.value
        }
    }
}
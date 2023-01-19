package com.halfa.islami.ui

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halfa.islami.models.PrayerTimesResponse
import com.halfa.islami.repos.PrayerTimesRepository

class HomeViewModel() : ViewModel() {


    fun getPrayerTimings(): MutableLiveData<List<Map<String, String>>> {

        return PrayerTimesRepository.getPrayerTimingsMutable()
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

        if (PrayerTimesRepository.prayersTimes.value?.isEmpty() == true) {
            PrayerTimesRepository.getPrayers(
                longi,
                lati,
                successCallBack
            )
        } else {
            PrayerTimesRepository.prayersTimes.value = PrayerTimesRepository.prayersTimes.value
        }
    }

}
package com.halfa.islami.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.halfa.islami.R
import com.halfa.islami.databinding.FragmentPrayerTimesBinding
import com.halfa.islami.databinding.PrayerTymesItemBinding
import com.halfa.islami.models.PrayerTimingsData
import com.halfa.islami.ui.HomeViewModel
import com.halfa.islami.utils.AlarmReceiver
import com.halfa.islami.utils.Utils
import java.util.*

class PrayerTimesFragment : Fragment() {
    lateinit var binding: FragmentPrayerTimesBinding
    lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment

        binding = FragmentPrayerTimesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]


        viewModel.getPrayerTimings().observe(viewLifecycleOwner) {
            initRecycler(it)
        }
//        viewModel.getPrayerTimings()
        return binding.root
    }

    private fun initRecycler(prayerTimingsData: PrayerTimingsData) {

        binding.prayersList.removeAllViews()


        val FajrItem = getItem("Fajr", prayerTimingsData.timings.fajr, 0)
        val DhuhrItem = getItem("Dhuhr", prayerTimingsData.timings.dhuhr, 0)
        val AsrItem = getItem("Asr", prayerTimingsData.timings.asr, 0)
        val MagribItem = getItem("Maghrib", prayerTimingsData.timings.maghrib, 0)
        val IshaItem = getItem("Isha", prayerTimingsData.timings.isha, 0)




        binding.prayersList.addView(FajrItem)
        binding.prayersList.addView(DhuhrItem)
        binding.prayersList.addView(AsrItem)
        binding.prayersList.addView(MagribItem)
        binding.prayersList.addView(IshaItem)


    }

    fun getItem(pName: String, pTime: String, pId: Int): View {
        val prayerItem: PrayerTymesItemBinding = PrayerTymesItemBinding.bind(
            Utils.inflateLayout(
                R.layout.prayer_tymes_item,
                LinearLayout(context)
            )
        )
        prayerItem.prayerName.text = pName
        prayerItem.prayerTime.text = pTime
        return prayerItem.root

    }

    companion object {

    }
}
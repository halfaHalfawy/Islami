package com.halfa.islami.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.halfa.islami.R
import com.halfa.islami.databinding.FragmentPrayerTimesBinding
import com.halfa.islami.databinding.PrayerTymesItemBinding
import com.halfa.islami.ui.HomeViewModel
import com.halfa.islami.utils.Utils

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

        viewModel.getPrayerTimings().observe(
            viewLifecycleOwner
        ) {
            initRecycler(it)
        }
        return binding.root
    }

    private fun initRecycler(listo: List<Map<String, String>>) {

        binding.prayersList.removeAllViews()

        listo.forEach {


            val prayerItem: PrayerTymesItemBinding = PrayerTymesItemBinding.bind(
                Utils.inflateLayout(
                    R.layout.prayer_tymes_item,
                    LinearLayout(context)
                )
            )

            prayerItem.prayerName.text = it.keys.first()
            prayerItem.prayerTime.text = it[it.keys.first()]
            binding.prayersList.addView(prayerItem.root)
        }
    }

    companion object {

    }
}
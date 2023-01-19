package com.halfa.islami.ui.fragments

import android.content.pm.PackageManager

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.halfa.islami.R
import com.halfa.islami.databinding.FragmentHomeBinding
import com.halfa.islami.ui.HomeViewModel
import com.halfa.islami.utils.LocationUtils
import com.halfa.islami.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding;
    lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentHomeBinding.bind(
                inflater.inflate(
                    R.layout.fragment_home,
                    container, false
                )
            )

        binding.prayerTimesCard.setOnClickListener(
            View.OnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_homeFragment_to_prayerTimesFragment)
            })


        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        requestFineLocationPermission(requireContext())
        LocationUtils.getCurrentLocation(
            requireContext(),
            locationListener
        )

        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        viewModel.getPrayerTimesMutable().observe(viewLifecycleOwner) {
            binding.hijriDate.text = it.data.date.hijri.day + " " + it.data.date.hijri.month.en +
                    " " + it.data.date.hijri.year
            binding.gregorianDate.text = it.data.date.gregorian.date
            var (nexpPName, nexPrayerTimeUntilIt) = Utils.getNextPrayer(
                it.data.timings.fajr,
                it.data.timings.dhuhr,
                it.data.timings.asr,
                it.data.timings.maghrib,
                it.data.timings.isha
            )
            binding.nextPrayer.text = nexpPName + ", "
            runWaitingTimeCountDown(nexPrayerTimeUntilIt)

        }

        viewModel.getPrayerTimings().observe(
            viewLifecycleOwner
        ) {


//            initRecycler(it)
        }
    }

    private fun runWaitingTimeCountDown(nexPrayerTimeUntilIt: Date) {

        var stf = SimpleDateFormat("HH:mm:ss")

        startCountdown(stf.format(nexPrayerTimeUntilIt), binding.nextPrayerWaitingTime)

    }

    fun startCountdown(time: String, textView: TextView) {
        var currentTime = time.split(":")
        var hours = currentTime[0].toInt()
        var minutes = currentTime[1].toInt()
        var seconds = currentTime[2].toInt()

        val timer = object : CountDownTimer(36000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (seconds > 0) {
                    seconds--
                } else if (minutes > 0) {
                    minutes--
                    seconds = 59
                } else if (hours > 0) {
                    hours--
                    minutes = 59
                    seconds = 59
                } else {
                    cancel()
                    textView.text = "00:00:00"
                }
                textView.text = "${hours.toString().padStart(2, '0')}:${
                    minutes.toString().padStart(2, '0')
                }:${seconds.toString().padStart(2, '0')}"
            }

            override fun onFinish() {
                textView.text = "00:00:00"
            }
        }
        timer.start()
    }


    companion object {
        private const val TAG = "HomeFragment"
    }

    private val REQUEST_CODE_FINE_LOCATION = 1

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    LocationUtils.getCurrentLocation(requireContext(), locationListener)

                    // Permission was granted, do something here
                } else {
                    Toast.makeText(requireContext(), "permissionDenied", Toast.LENGTH_LONG).show()
                    // Permission denied, show a message or do something else
                }
                return
            }
            else -> {
                // Handle other permission requests
            }
        }
    }

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onViewCreated: ${location.longitude} , ${location.latitude}")

            viewModel.getPrayers(
                location.longitude,
                location.latitude,
                binding.root,
                successCallBack = fun(
                    isSuccess,
                    messages
                ) {


                    if (!isSuccess) {
//                        Navigation.findNavController(view).ge
//                        Navigation.findNavController(view)
//                            .navigate(R.id.action_prayerTimesFragment_to_homeFragment)
//                        val navController = view?.findNavController()
//                        if (navController != null) {
//                            navController.popBackStack()
//                        }

                        Toast.makeText(requireContext(), messages, Toast.LENGTH_LONG).show()
//                        if(Navigation.findNavController().popBackStack())
//                        { Navigation.findNavController(view).popBackStack() }
                    }

                })
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}







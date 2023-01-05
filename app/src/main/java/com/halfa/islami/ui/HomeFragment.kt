package com.halfa.islami.ui

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.halfa.islami.R
import com.halfa.islami.databinding.FragmentHomeBinding
import com.halfa.islami.databinding.PrayerTymesItemBinding
import com.halfa.islami.utils.LocationUtils
import com.halfa.islami.utils.Utils

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

        viewModel.prayersTimes.observe(
            viewLifecycleOwner
        ) {
            binding.prayersList.removeAllViews()

            it.forEach {
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
    }

    fun initRecycler() {

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

    val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d(TAG, "onViewCreated: ${location.longitude} , ${location.latitude}")

         viewModel. getPrayers(location.longitude, location.latitude,requireContext())
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}







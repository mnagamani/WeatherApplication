package com.example.weather.screen.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.weather.R
import com.example.weather.databinding.SearchFragmentLayoutBinding
import com.example.weather.screen.search.viewmodel.SearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.android.support.AndroidSupportInjection
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import com.example.weather.adapter.WeatherRecyclerViewAdapter as WeatherRecyclerViewAdapter1


private const val LOCATION_INTERVAL_REQUEST = 2L

/**
 * Fragment to show user search and show weather details
 */
class SearchFragment : Fragment() {

    //databinding
    private lateinit var binding: SearchFragmentLayoutBinding

    //factory injection that handles view model injections
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    val viewModel: SearchViewModel by viewModels<SearchViewModel> {
        viewModelFactory
    }

    //location request
    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        interval = TimeUnit.SECONDS.toMillis(LOCATION_INTERVAL_REQUEST)
    }

    //location provider
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    /**
     * registering activity result callback for multiple permissions(location)
     */
    private val multiPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            var isLocationAvailable = false
            map.entries.forEach { entry ->
                when (entry.key) {
                    Manifest.permission.ACCESS_FINE_LOCATION ->
                        isLocationAvailable = isLocationAvailable || entry.value

                    Manifest.permission.ACCESS_COARSE_LOCATION ->
                        isLocationAvailable = isLocationAvailable || entry.value
                }
            }

            //location permission is granted
            viewModel.setIsAccessLocationEnabled(isLocationAvailable)

        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO: had more time, would implement jetpack compose
        //TODO: had more time, would have implemented another details fragment, to navigate to see more weather information
        binding =
            DataBindingUtil.inflate(inflater, R.layout.search_fragment_layout, container, false)
        binding.lifecycleOwner = this
        binding.searchViewModel = viewModel
        initViews()
        initObservers()
        return binding.root
    }

    /**
     * Function for starting multiple permission request
     */
    private fun requestMultiplePermissions() {
        multiPermissionCallback.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun initObservers() {

        viewModel.askPermissions.observe(viewLifecycleOwner) { askPermission ->
            if (askPermission)
                requestMultiplePermissions()
            else {
                viewModel.showDetailsByCityNameIfAvailable()
            }
        }
        /*
        TODO : had more time, would extract all the location accessing information  and business logic from the fragment and pass it on to the view model
         */
        viewModel.isAccessLocationEnabled.observe(viewLifecycleOwner) { isLocationAvailable ->
            if (isLocationAvailable) {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            } else {
                viewModel.showDetailsByCityNameIfAvailable()
            }
        }

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                //TODO had more time, would custom style the message shown
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews() {
        val weatherListAdapter = WeatherRecyclerViewAdapter1()
        binding.weatherListRecycler.adapter = weatherListAdapter
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    /**
     * callback for once location is received
     */
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation?.let {
                viewModel.showDetailsByLatLong(it.latitude, it.longitude)
            }
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

}
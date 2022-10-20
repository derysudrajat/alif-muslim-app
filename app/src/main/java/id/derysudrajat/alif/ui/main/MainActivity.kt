package id.derysudrajat.alif.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.compose.page.HomePage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.data.model.TimingSchedule
import id.derysudrajat.alif.databinding.ActivityMainBinding
import id.derysudrajat.alif.ui.activity.ProgressActivity
import id.derysudrajat.alif.ui.activity.ProgressActivityViewModel
import id.derysudrajat.alif.ui.calendar.CalendarActivity
import id.derysudrajat.alif.utils.LocationUtils
import id.derysudrajat.alif.utils.LocationUtils.checkLocationPermission

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val activityViewModel: ProgressActivityViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                HomePage(
                    homeViewModel.currentSchedule.hijriDate,
                    homeViewModel.timingSchedule,
                    activityViewModel.activities,
                    homeViewModel.locationAddress,
                    homeViewModel.nextPray,
                    homeViewModel.descNextPray,
                    homeViewModel::getIntervalText,
                    this::updatePrayer,
                    this::toDetailCalendar,
                    this::toProgressActivity
                )
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission { requestLocationPermission() }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let { location ->
                currentLocation = location
                homeViewModel.getLocationAddress(this, location)
                homeViewModel.getPrayerSchedule(
                    location.latitude,
                    location.longitude,
                    Timestamp.now()
                )
            }
        }
        activityViewModel.getTodayActivity()
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.getTodayActivity()
    }

    private fun toDetailCalendar() = startActivity(Intent(this, CalendarActivity::class.java))
    private fun toProgressActivity() = startActivity(Intent(this, ProgressActivity::class.java))

    private fun updatePrayer(
        timingSchedule: TimingSchedule,
        prayerTime: String,
        isReminded: Boolean,
        position: Int
    ) {
        homeViewModel.updatePrayer(this, timingSchedule, prayerTime, isReminded, position)
    }

    @SuppressLint("NewApi")
    fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions -> LocationUtils.handlePermission(permissions) }
        LocationUtils.launchPermission(locationPermissionRequest)
    }
}
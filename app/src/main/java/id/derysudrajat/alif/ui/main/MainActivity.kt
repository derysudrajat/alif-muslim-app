package id.derysudrajat.alif.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.R
import id.derysudrajat.alif.data.model.*
import id.derysudrajat.alif.databinding.ActivityMainBinding
import id.derysudrajat.alif.ui.activity.ProgressActivity
import id.derysudrajat.alif.ui.activity.ProgressActivityViewModel
import id.derysudrajat.alif.ui.calendar.CalendarActivity
import id.derysudrajat.alif.utils.LocationUtils
import id.derysudrajat.alif.utils.LocationUtils.checkLocationPermission
import id.derysudrajat.alif.utils.SkeletonUtils.buildSkeleton
import id.derysudrajat.alif.utils.SkeletonUtils.configure
import id.derysudrajat.alif.utils.TimeUtils.fullDate
import id.derysudrajat.alif.utils.TimeUtils.hour
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val activityViewModel: ProgressActivityViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var calendarAdapter: CalendarAdapter
    private var progressAdapter = ActivityTaskAdapter(listOf(), ::toProgressActivity)

    private val scope = lifecycleScope
    private lateinit var skeletonRv: Skeleton
    private lateinit var skeletonCard: Skeleton

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        skeletonRv = binding.rvSchedule.buildSkeleton(R.layout.item_schedule, 7)
        skeletonCard = binding.cardContainer.createSkeleton().apply { configure() }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission { requestLocationPermission() }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            populateAddress(it)
            homeViewModel.getPrayerSchedule(it.latitude, it.longitude, Timestamp.now())
        }
        activityViewModel.getTodayActivity()

        scope.launch { homeViewModel.currentSchedule.collect(::populateCurrentSchedule) }
        scope.launch { homeViewModel.timingSchedule.collect(::populateTimingSchedule) }
        scope.launch { homeViewModel.nextPray.collect { binding.tvNextPray.text = it } }
        scope.launch {
            homeViewModel.descNextPray.collect { binding.tvNearestScheduleName.text = it }
        }
        scope.launch { homeViewModel.isLoading.collect(::populateLoading) }
        scope.launch { activityViewModel.activities.collect(::populateTask) }
    }

    private fun populateTask(it: List<ProgressTask>) {
        progressAdapter = ActivityTaskAdapter(it, ::toProgressActivity)
        if (isAllAdapterInitialized()) updateAdapter()
    }

    private fun updateAdapter() {
        binding.rvSchedule.apply {
            itemAnimator = DefaultItemAnimator()
            adapter = ConcatAdapter(progressAdapter, calendarAdapter, scheduleAdapter)
        }
    }

    private fun populateLoading(it: Boolean) {
        if (it) {
            skeletonRv.showSkeleton()
            skeletonCard.showSkeleton()
        } else {
            skeletonRv.showOriginal()
            skeletonCard.showOriginal()
        }
    }

    private fun populateAddress(it: Location) {
        Geocoder(this, Locale.getDefault()).apply {
            getFromLocation(it.latitude, it.longitude, 1).first().let { address ->
                binding.tvAddress.text = buildString {
                    append(address.locality).append(", ")
                    append(address.subAdminArea)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.getTodayActivity()
    }

    private fun populateCurrentSchedule(it: Schedule) {
        with(binding) {
            tvDate.text = Timestamp.now().fullDate
            it.hijriDate.let { date ->
                tvDateIslamic.text = buildString {
                    append("${date.day} ${date.monthDesignation} ${date.year} ${date.yearDesignation}")
                }
                calendarAdapter = CalendarAdapter(date, ::toDetailCalendar)
            }
        }
    }

    private fun toDetailCalendar() = startActivity(Intent(this, CalendarActivity::class.java))
    private fun toProgressActivity() = startActivity(Intent(this, ProgressActivity::class.java))

    private fun populateTimingSchedule(it: TimingSchedule) {
        with(binding) {
            val nearestSchedule = it.getNearestSchedule(Timestamp.now())
            tvNearestSchedule.text = nearestSchedule.time
            homeViewModel.getIntervalText(it, nearestSchedule)
            tvNearestScheduleName.text = it.getScheduleName(nearestSchedule)

            ivSound.setImageResource(
                if (nearestSchedule.isReminded) R.drawable.ic_sound_on else R.drawable.ic_sound_off
            )
            tvButtonSound.text = if (nearestSchedule.isReminded) "On" else "Off"
            laPray.apply {
                setAnimation(
                    if (Timestamp.now().hour in (19..23) || Timestamp.now().hour in (0..5)) R.raw.an_night else R.raw.an_day
                )
                playAnimation()
            }
            scheduleAdapter = ScheduleAdapter(
                it.toList(), it
            ) { timingSchedule, prayerTime, isReminded, position ->
                homeViewModel.updatePrayer(
                    this@MainActivity, timingSchedule, prayerTime, isReminded, position
                )
            }
            if (isAllAdapterInitialized()) updateAdapter()
        }
    }

    private fun isAllAdapterInitialized(): Boolean {
        return this::calendarAdapter.isInitialized && this::scheduleAdapter.isInitialized
    }


    @SuppressLint("NewApi")
    fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions -> LocationUtils.handlePermission(permissions) }
        LocationUtils.launchPermission(locationPermissionRequest)
    }
}
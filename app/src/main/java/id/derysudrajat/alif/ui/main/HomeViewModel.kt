package id.derysudrajat.alif.ui.main

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import id.derysudrajat.alif.data.model.*
import id.derysudrajat.alif.repo.PrayerRepository
import id.derysudrajat.alif.repo.States
import id.derysudrajat.alif.service.PrayerAlarm
import id.derysudrajat.alif.utils.TimeUtils.day
import id.derysudrajat.alif.utils.TimeUtils.hour
import id.derysudrajat.alif.utils.TimeUtils.month
import id.derysudrajat.alif.utils.TimeUtils.year
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PrayerRepository,
    private val prayerAlarm: PrayerAlarm
) : ViewModel() {

    var currentSchedule = MutableStateFlow(Schedule.EMPTY)

    var timingSchedule = MutableStateFlow(TimingSchedule.EMPTY)
    private var currentTimingSchedule = TimingSchedule.EMPTY
    var isLoading = MutableStateFlow(true)

    var nextPray = MutableStateFlow("-")
    var descNextPray = MutableStateFlow("-")
    private lateinit var countDownTimer: CountDownTimer


    fun getPrayerSchedule(lat: Double, long: Double, date: Timestamp) = viewModelScope.launch {
        repository.getSchedule(lat, long, date.month, date.year).collect {
            when (it) {
                is States.Loading -> {
                    Log.d("TAG", "getPrayerSchedule: loading")
                    isLoading.value = true
                }
                is States.Success -> {
                    isLoading.value = false
                    it.data.find { sc ->
                        sc.georgianDate.day == if (date.hour > 20) date.day + 1 else date.day
                    }?.let { schedule ->
                        viewModelScope.launch { currentSchedule.emit(schedule) }
                        getReminderPrayer(schedule.timingSchedule)
                        viewModelScope.launch { timingSchedule.emit(currentTimingSchedule) }
                    }
                }
                is States.Failed -> {
                    isLoading.value = false
                    Log.d("TAG", "getPrayerSchedule: failed = ${it.message}")
                }
            }
        }
    }

    private fun getReminderPrayer(timingSchedule: TimingSchedule) = viewModelScope.launch {
        repository.getAllReminder().collect {
            if (it.isEmpty()) repository.addAllReminders(PrayerReminder.EMPTY)
            else updateReminder(it, timingSchedule)
        }
    }

    private fun updateReminder(
        listOfReminder: List<PrayerReminder>,
        timingSchedule: TimingSchedule
    ) {
        val listSchedule = timingSchedule.toList()
        if (listSchedule.isNotEmpty()) {
            listOfReminder.forEach { reminder ->
                listSchedule[reminder.index].isReminded = reminder.isReminded
            }
            currentTimingSchedule = listSchedule.toTimingSchedule()
            viewModelScope.launch { this@HomeViewModel.timingSchedule.emit(TimingSchedule.EMPTY) }
            viewModelScope.launch { this@HomeViewModel.timingSchedule.emit(currentTimingSchedule) }
        }
    }

    fun updatePrayer(
        context: Context,
        timingSchedule: TimingSchedule,
        prayerTime: String,
        isReminded: Boolean,
        position: Int
    ) = viewModelScope.launch {
        countDownTimer.onFinish()
        val prayerReminder = PrayerReminder(position, prayerTime, isReminded)
        if (isReminded) prayerAlarm.setPrayerAlarm(context, prayerReminder)
        else prayerAlarm.cancelAlarm(context, prayerReminder)
        viewModelScope.launch {
            repository.updateReminder(PrayerReminder(position, prayerTime, isReminded))
            getReminderPrayer(timingSchedule)
        }
    }

    fun getIntervalText(timingSchedule: TimingSchedule, prayer: Prayer) = viewModelScope.launch {
        val now = Timestamp.now()
        val diff: Long = Date(
            Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, if (now.hour > 20) now.day + 1 else now.day)
                set(Calendar.HOUR_OF_DAY, prayer.time.hour)
                set(Calendar.MINUTE, prayer.time.minutes)
            }.time.time
        ).time - now.toDate().time
        if (this@HomeViewModel::countDownTimer.isInitialized) countDownTimer.cancel()
        countDownTimer = object : CountDownTimer(diff, 1000) {
            override fun onTick(progress: Long) {
                val seconds = progress / 1000
                val minutes = seconds / 60
                val hours = minutes / 60
                println(seconds)
                nextPray.value = "${hours}h ${minutes - (hours * 60)}m ${seconds - (minutes * 60)}s"
                descNextPray.value = buildString {
                    append(" to ")
                    append(timingSchedule.getScheduleName(prayer))
                }
            }

            override fun onFinish() {
                if (this@HomeViewModel::countDownTimer.isInitialized) countDownTimer.cancel()
                nextPray.value = "Now"
                descNextPray.value = buildString {
                    append(" it's time to pray ")
                    append(timingSchedule.getScheduleName(prayer))
                }
            }
        }.start()
    }

}
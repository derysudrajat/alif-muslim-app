package id.derysudrajat.alif.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.compose.ui.components.*
import id.derysudrajat.alif.data.model.DateSchedule
import id.derysudrajat.alif.data.model.Prayer
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.data.model.TimingSchedule

@Composable
fun HomePage(
    calendar: DateSchedule,
    timingSchedule: TimingSchedule,
    progressListTask: List<ProgressTask>,
    locationAddress: String,
    timeNextPray: String,
    descNextPray: String,
    getInterval: (timingSchedule: TimingSchedule, nearestSchedule: Prayer) -> Unit,
    onSetReminder: (timingSchedule: TimingSchedule, prayerTime: String, isReminded: Boolean, position: Int) -> Unit,
    goToDetailCalendar: () -> Unit,
    goToProgressActivity: () -> Unit,
) {
    Scaffold {
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            item { ItemMainDate(calendar) }
            item {
                ItemTimingSchedule(
                    timingSchedule,
                    locationAddress,
                    timeNextPray,
                    descNextPray,
                    getInterval
                )
            }
            item { ItemProgressActivity(progressListTask, goToProgressActivity) }
            item { ItemCalendar(calendar, goToDetailCalendar) }
            item { ItemSchedule(timingSchedule.imsak, timingSchedule, onSetReminder) }
            item { ItemSchedule(timingSchedule.fajr, timingSchedule, onSetReminder) }
            item { ItemSchedule(timingSchedule.dhuhr, timingSchedule, onSetReminder) }
            item { ItemSchedule(timingSchedule.asr, timingSchedule, onSetReminder) }
            item { ItemSchedule(timingSchedule.maghrib, timingSchedule, onSetReminder) }
            item { ItemSchedule(timingSchedule.isha, timingSchedule, onSetReminder) }
        }
    }
}

@SuppressLint("MissingPermission")
@Preview
@Composable
private fun PreviewHomePage() {
    HomePage(
        dummyCalendar, dummyTimingSchedule, dummyListProgressTask, dummyLocationAddress,
        dummyNextPray, dummyDescNextPray, { _, _ -> }, { _, _, _, _ -> }, {}, {}
    )
}
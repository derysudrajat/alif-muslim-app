package id.derysudrajat.alif.compose.page

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.compose.ui.components.ItemCalendar
import id.derysudrajat.alif.compose.ui.components.ItemMainDate
import id.derysudrajat.alif.compose.ui.components.ItemProgressActivity
import id.derysudrajat.alif.compose.ui.components.ItemSchedule
import id.derysudrajat.alif.compose.ui.components.ItemTimingSchedule
import id.derysudrajat.alif.compose.ui.components.dummyCalendar
import id.derysudrajat.alif.compose.ui.components.dummyDescNextPray
import id.derysudrajat.alif.compose.ui.components.dummyListProgressTask
import id.derysudrajat.alif.compose.ui.components.dummyLocationAddress
import id.derysudrajat.alif.compose.ui.components.dummyNextPray
import id.derysudrajat.alif.compose.ui.components.dummyTimingSchedule
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
    Scaffold(Modifier.padding(16.dp)) {
        LazyColumn(contentPadding = it) {
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
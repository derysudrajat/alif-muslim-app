package id.derysudrajat.alif.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.now
import id.derysudrajat.alif.domain.model.PrayerData
import id.derysudrajat.alif.domain.model.PrayerScheduleData
import id.derysudrajat.alif.domain.model.TimingSchedule
import id.derysudrajat.alif.services.Location
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeContent(
    goToCalendar: (Location) -> Unit
) {
    val viewmodel = koinViewModel<MainViewModel>()
    val (dateToday, setDateToday) = remember { mutableStateOf("") }
    val (currentLocation, setCurrentLocation) = remember { mutableStateOf(Location(0.0, 0.0)) }
    val (todaySchedule, setTodaySchedule) = remember { mutableStateOf(TimingSchedule.Empty) }
    val (todayPrayerSchedule, setTodayPrayerSchedule) = remember { mutableStateOf(PrayerScheduleData.Empty) }
    val (nearestSchedule, setNearestSchedule) = remember { mutableStateOf(PrayerData.Empty) }
    val (dateTodayHijri, setDateTodayHijri) = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val date = LocalDate.now()
        setDateToday(date.toUiString())

        viewmodel.requestLocation(
            onSuccess = {
                setCurrentLocation(it)
                viewmodel.getSchedules(
                    location = it,
                    onSuccess = { todaySchedule, listMonthSchedule ->
                        println("todaySchedule.timingSchedule = ${todaySchedule.timingSchedule}")
                        setTodaySchedule(todaySchedule.timingSchedule)
                        setTodayPrayerSchedule(todaySchedule)
                        setDateTodayHijri(
                            buildString {
                                append(todaySchedule.hijriDate.day).append(" ")
                                append(todaySchedule.hijriDate.monthDesignation).append(" ")
                                append(todaySchedule.hijriDate.year).append(" ")
                                append(todaySchedule.hijriDate.yearDesignation)
                            }
                        )
                    },
                    onFailed = {

                    }
                )
            },
            onFailed = {

            }
        )
    }
    Scaffold(
        containerColor = AppColor.Background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                HeaderHome(
                    georgianDate = dateToday,
                    hijriDate = dateTodayHijri,
                    goToQuran = {},
                    goToCompass = {}
                )
            }
            item {
                ScheduleTimeCard(todaySchedule, onNearestScheduleChange = setNearestSchedule)
            }
            item {
                HomeTaskItem()
            }
            item {
                HomeCalendarItem(
                    schedule = todayPrayerSchedule,
                    onClick = { goToCalendar(currentLocation) }
                )
            }
            item {
                ScheduleCard(timingSchedule = todaySchedule, nearestSchedule = nearestSchedule)
            }
        }
    }
}

fun LocalDate.toUiString(): String {
    val dayOfWeek = this.dayOfWeek.name.lowercase()
        .replaceFirstChar { it.uppercase() }
        .take(3)
    val day = day.toString().padStart(2, '0')
    val month = this.month.name.lowercase()
        .replaceFirstChar { it.uppercase() }
    val year = this.year
    return "$dayOfWeek, $day $month $year"
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewHomeContent() {
    MaterialTheme {
        HomeContent(
            goToCalendar = {}
        )
    }
}
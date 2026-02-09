package id.derysudrajat.alif.ui.screens.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kizitonwose.calendar.core.now
import id.derysudrajat.alif.domain.model.PrayerScheduleData
import id.derysudrajat.alif.services.Location
import id.derysudrajat.alif.ui.screens.main.MainViewModel
import kotlinx.datetime.YearMonth
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun IslamicCalendarScreen(
    location: Location,
    onBack: () -> Unit
) {

    val mainViewModel = koinViewModel<MainViewModel>()
    val (monthSchedule, setMonthSchedule) = remember { mutableStateOf(emptyList<PrayerScheduleData>()) }

    LaunchedEffect(location) {
        if (location != Location(0.0, 0.0)) {
            val yearMonth = YearMonth.now()
            mainViewModel.getSchedulesInMonth(
                location = location,
                month = yearMonth.month.ordinal + 1,
                year = yearMonth.year,
                onSuccess = setMonthSchedule,
                onFailed = {
                    println("Failed: $it")
                }
            )
        }
    }

    IslamicCalendarContent(
        monthSchedules = monthSchedule,
        onBack = onBack,
        onRequestSchedule = { month, year ->
            if (location != Location(0.0, 0.0)) mainViewModel.getSchedulesInMonth(
                location = location,
                month = month,
                year = year,
                onSuccess = setMonthSchedule,
                onFailed = {
                    println("Failed: $it")
                }
            )
        }
    )
}
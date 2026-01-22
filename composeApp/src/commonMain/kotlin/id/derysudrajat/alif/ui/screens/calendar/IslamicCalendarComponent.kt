package id.derysudrajat.alif.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

@Composable
fun IslamicCalendarComponent(
    modifier: Modifier = Modifier
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember {
        firstDayOfWeekFromLocale(
            locale = Locale("in-ID")
        )

    }


    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )



    HorizontalCalendar(
        modifier = modifier,
        state = state,
        dayContent = {
            Day(it, state.firstVisibleMonth.yearMonth, listOf(true, false).random())
        },
        monthHeader = { month ->
            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleMonth(month)
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        },
    )
}

fun YearMonth.isToday(day: CalendarDay): Boolean {
    val currentDay = LocalDate.now()
    return this.year == currentDay.year
            && this.month.ordinal == currentDay.month.ordinal
            && day.date.day == currentDay.day
}

@Composable
private fun TitleMonth(month: CalendarMonth) {
    Row {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = buildString {
                append(
                    month.yearMonth.month.name
                        .lowercase()
                        .replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase() else it.toString()
                        }
                )
                append(" ")
                append(month.yearMonth.year)
            },
            color = AppColor.Text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    month: YearMonth,
    haveHoliday: Boolean = false
) {
    Box(
        modifier = Modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        when {
            month.isToday(day) -> DayToday(day)
            haveHoliday -> DayEvent(day)
            else -> Text(
                text = day.date.day.toString(),
                color = if (day.position == DayPosition.MonthDate)
                    AppColor.Text
                else AppColor.Text.copy(0.3f)
            )
        }
    }
}

@Composable
private fun DayEvent(day: CalendarDay) {
    Column(
        modifier = Modifier.aspectRatio(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(Color.Transparent),
        )
        Text(
            text = day.date.day.toString(),
            color = AppColor.Text
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(AppColor.Primary.Main.copy(0.8f)),
        )
    }
}

@Composable
private fun DayToday(day: CalendarDay) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(AppColor.Primary.Main),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.day.toString(),
            color = AppColor.White.Main
        )
    }
}

@Composable
private fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.name.take(3)
                    .lowercase()
                    .replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    },
                color = AppColor.Text
            )
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewIslamicCalendarComponent() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth().background(AppColor.Background)) {
            IslamicCalendarComponent(
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}
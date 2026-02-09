package id.derysudrajat.alif.domain.model

import id.derysudrajat.alif.data.remote.response.ScheduleResponse
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class PrayerScheduleData(
    val timingSchedule: TimingSchedule,
    val georgianDate: DateSchedule,
    val hijriDate: DateSchedule,
    val metaSchedule: MetaSchedule
) {
    companion object {
        val Empty = ScheduleResponse().toSchedule()
    }
}

data class MetaSchedule(
    val latitude: Double,
    val longitude: Double,
    val timeZone: String
)

data class DateSchedule(
    val day: Int,
    val month: Int,
    val monthDesignation: String,
    val year: Int,
    val yearDesignation: String,
    val weekday: String,
    val date: String,
    val holidays: List<String>
)

data class TimingSchedule(
    val imsak: PrayerData,
    val fajr: PrayerData,
    val sunrise: PrayerData,
    val dhuhr: PrayerData,
    val asr: PrayerData,
    val maghrib: PrayerData,
    val isha: PrayerData
) {
    companion object {
        val Empty = TimingSchedule(
            PrayerData.Empty,
            PrayerData.Empty,
            PrayerData.Empty,
            PrayerData.Empty,
            PrayerData.Empty,
            PrayerData.Empty,
            PrayerData.Empty
        )
    }
}

data class PrayerData(
    val time: String,
    var isReminded: Boolean
) {
    companion object {
        val Empty = PrayerData("-", false)
    }
}

sealed class PrayerStatus {
    data class Prepare(val prayer: PrayerData) : PrayerStatus()
    data class ItsTime(val prayer: PrayerData) : PrayerStatus()
    data class Next(val prayer: PrayerData) : PrayerStatus()
}

fun getPrayerStatus(
    schedules: List<PrayerData>,
    currentMinutes: Int
): PrayerStatus {

    val activePrayer = schedules.firstOrNull {
        val pMin = parseToMinutes(it.time)
        val minutesPassed = (currentMinutes - pMin + 1440) % 1440

        minutesPassed in 0..5
    }

    if (activePrayer != null) {
        return PrayerStatus.ItsTime(activePrayer)
    }

    val preparingPrayer = schedules.firstOrNull {
        val pMin = parseToMinutes(it.time)
        val minutesUntil = (pMin - currentMinutes + 1440) % 1440

        minutesUntil in 1..5
    }

    if (preparingPrayer != null) {
        return PrayerStatus.Prepare(preparingPrayer)
    }

    val nextPrayer = schedules.minByOrNull {
        val pMin = parseToMinutes(it.time)
        val minutesUntil = (pMin - currentMinutes + 1440) % 1440
        minutesUntil
    }!!

    return PrayerStatus.Next(nextPrayer)
}

fun parseToMinutes(timeString: String): Int {
    val cleanTime = timeString.substringBefore(" ").trim()
    val parts = cleanTime.split(":")
    val hour = parts[0].toInt()
    val minute = parts[1].toInt()
    return (hour * 60) + minute
}

fun getTimeUntil(targetTimeStr: String, now: LocalDateTime): String {
    val timeZone = TimeZone.currentSystemDefault()
    val cleanTime = targetTimeStr.substringBefore(" ").trim()
    val (targetHour, targetMinute) = cleanTime.split(":").map { it.toInt() }
    var targetDateTime = LocalDateTime(
        year = now.year,
        month = now.month,
        day = now.day,
        hour = targetHour,
        minute = targetMinute,
        second = 0,
        nanosecond = 0
    )

    if (now > targetDateTime) {
        val tomorrowDate = now.date.plus(DatePeriod(days = 1))
        targetDateTime = LocalDateTime(
            date = tomorrowDate,
            time = targetDateTime.time
        )
    }

    val nowInstant = now.toInstant(timeZone)
    val targetInstant = targetDateTime.toInstant(timeZone)

    val diffInSeconds = targetInstant.minus(nowInstant).inWholeSeconds

    val hours = diffInSeconds / 3600
    val minutes = (diffInSeconds % 3600) / 60
    val seconds = diffInSeconds % 60

    return buildString {
        if (hours != 0L) append("${hours}h ")
        if (minutes != 0L) append("${minutes}m ")
        append("${seconds}s")
    }
}

fun TimingSchedule.getScheduleName(time: PrayerData): String {
    return when (this.toList().indexOf(time)) {
        0 -> "Imsak"
        1 -> "Farj"
        2 -> "Sunrise"
        3 -> "Dhuhr"
        4 -> "Asr"
        5 -> "Maghrib"
        6 -> "Isha"
        else -> "-"
    }
}

fun TimingSchedule.getNearestSchedule(
    timestamp: LocalDateTime = Clock.System.now().toLocalDateTime(
        TimeZone.currentSystemDefault()
    )
): PrayerData =
    this.toList()
        .filter { it.time.hour >= timestamp.hour }
        .firstOrNull {
            if (it.time.hour == timestamp.hour) it.time.minutes >= timestamp.minute
            else it.time.hour >= timestamp.hour
        } ?: this.toList().minByOrNull { it.time.hour } ?: PrayerData.Empty

fun TimingSchedule.toList() = if (this.fajr.time != "-") listOf(
    this.imsak, this.fajr, this.sunrise, this.dhuhr, this.asr, this.maghrib, this.isha,
) else listOf()

fun List<PrayerData>.toTimingSchedule() =
    TimingSchedule(this[0], this[1], this[2], this[3], this[4], this[5], this[6])

val String.hour get() : Int = if (this != "-") this.split(":", " ").first().toInt() else 0
val String.minutes get() : Int = if (this != "-") this.split(":", " ")[1].toInt() else 0

fun List<ScheduleResponse>.toSchedule(): MutableList<PrayerScheduleData> {
    val listOfSchedule = mutableListOf<PrayerScheduleData>()
    this.forEach { listOfSchedule.add(it.toSchedule()) }
    return listOfSchedule
}

fun ScheduleResponse.toSchedule(): PrayerScheduleData {
    return PrayerScheduleData(
        timingSchedule = TimingSchedule(
            imsak = PrayerData(this.timingResponse?.imsak ?: "-", false),
            fajr = PrayerData(this.timingResponse?.fajr ?: "-", false),
            sunrise = PrayerData(this.timingResponse?.sunrise ?: "-", false),
            dhuhr = PrayerData(this.timingResponse?.dhuhr ?: "-", false),
            asr = PrayerData(this.timingResponse?.asr ?: "-", false),
            maghrib = PrayerData(this.timingResponse?.maghrib ?: "-", false),
            isha = PrayerData(this.timingResponse?.isha ?: "-", false),
        ),
        georgianDate = DateSchedule(
            day = (this.dateResponse?.gregorian?.day ?: "0").toInt(),
            month = this.dateResponse?.gregorian?.monthResponse?.number ?: 0,
            monthDesignation = this.dateResponse?.gregorian?.monthResponse?.en ?: "",
            year = (this.dateResponse?.gregorian?.year ?: "0").toInt(),
            yearDesignation = "AD",
            weekday = this.dateResponse?.gregorian?.weekdayResponse?.en ?: "",
            date = this.dateResponse?.gregorian?.date ?: "",
            holidays = this.dateResponse?.gregorian?.holidays ?: listOf()
        ),
        hijriDate = DateSchedule(
            day = (this.dateResponse?.hijri?.day ?: "0").toInt(),
            month = this.dateResponse?.hijri?.monthResponse?.number ?: 0,
            monthDesignation = this.dateResponse?.hijri?.monthResponse?.en ?: "",
            year = (this.dateResponse?.hijri?.year ?: "0").toInt(),
            yearDesignation = "AH",
            weekday = this.dateResponse?.hijri?.weekdayResponse?.en ?: "",
            date = this.dateResponse?.hijri?.date ?: "",
            holidays = this.dateResponse?.hijri?.holidays ?: listOf()
        ),
        metaSchedule = MetaSchedule(
            latitude = this.metaResponse?.latitude ?: 0.0,
            longitude = this.metaResponse?.longitude ?: 0.0,
            timeZone = this.metaResponse?.timezone ?: ""
        )
    )
}
package id.derysudrajat.alif.data.model

import com.google.firebase.Timestamp
import id.derysudrajat.alif.repo.remote.response.ScheduleResponse
import id.derysudrajat.alif.utils.TimeUtils.hour
import id.derysudrajat.alif.utils.TimeUtils.minutes

data class Schedule(
    val timingSchedule: TimingSchedule,
    val georgianDate: DateSchedule,
    val hijriDate: DateSchedule,
    val metaSchedule: MetaSchedule
) {
    companion object {
        val EMPTY = ScheduleResponse().toSchedule()
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
    val imsak: Prayer,
    val fajr: Prayer,
    val sunrise: Prayer,
    val dhuhr: Prayer,
    val asr: Prayer,
    val maghrib: Prayer,
    val isha: Prayer
) {
    companion object {
        val EMPTY = TimingSchedule(
            Prayer.EMPTY,
            Prayer.EMPTY,
            Prayer.EMPTY,
            Prayer.EMPTY,
            Prayer.EMPTY,
            Prayer.EMPTY,
            Prayer.EMPTY
        )
    }
}

data class Prayer(
    val time: String,
    var isReminded: Boolean
) {
    companion object {
        val EMPTY = Prayer("-", false)
    }
}

fun TimingSchedule.getScheduleName(time: Prayer): String {
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

fun TimingSchedule.getNearestSchedule(timestamp: Timestamp): Prayer =
    this.toList()
        .filter { it.time.hour >= timestamp.hour }
        .firstOrNull {
            if (it.time.hour == timestamp.hour) it.time.minutes >= timestamp.minutes
            else it.time.hour >= timestamp.hour
        } ?: this.toList().minByOrNull { it.time.hour } ?: Prayer.EMPTY

fun TimingSchedule.toList() = if (this.fajr.time != "-") listOf(
    this.imsak, this.fajr, this.sunrise, this.dhuhr, this.asr, this.maghrib, this.isha,
) else listOf()

fun List<Prayer>.toTimingSchedule() =
    TimingSchedule(this[0], this[1], this[2], this[3], this[4], this[5], this[6])

val String.hour get() : Int = if (this != "-") this.split(":", " ").first().toInt() else 0
val String.minutes get() : Int = if (this != "-") this.split(":", " ")[1].toInt() else 0

fun List<ScheduleResponse>.toSchedule(): MutableList<Schedule> {
    val listOfSchedule = mutableListOf<Schedule>()
    this.forEach { listOfSchedule.add(it.toSchedule()) }
    return listOfSchedule
}

fun ScheduleResponse.toSchedule(): Schedule {
    return Schedule(
        timingSchedule = TimingSchedule(
            imsak = Prayer(this.timingResponse?.imsak ?: "-", false),
            fajr = Prayer(this.timingResponse?.fajr ?: "-", false),
            sunrise = Prayer(this.timingResponse?.sunrise ?: "-", false),
            dhuhr = Prayer(this.timingResponse?.dhuhr ?: "-", false),
            asr = Prayer(this.timingResponse?.asr ?: "-", false),
            maghrib = Prayer(this.timingResponse?.maghrib ?: "-", false),
            isha = Prayer(this.timingResponse?.isha ?: "-", false),
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

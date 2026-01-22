package id.derysudrajat.alif.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScheduleResponse(

    @SerialName("date")
    val dateResponse: DateResponse? = null,

    @SerialName("meta")
    val metaResponse: MetaResponse? = null,

    @SerialName("timings")
    val timingResponse: TimingResponse? = null
)

@Serializable
data class MetaResponse(

    @SerialName("timezone")
    val timezone: String? = null,

    @SerialName("latitude")
    val latitude: Double? = null,

    @SerialName("longitude")
    val longitude: Double? = null,
)

@Serializable
data class TimingResponse(

    @SerialName("Sunset")
    val sunset: String? = null,

    @SerialName("Asr")
    val asr: String? = null,

    @SerialName("Isha")
    val isha: String? = null,

    @SerialName("Fajr")
    val fajr: String? = null,

    @SerialName("Dhuhr")
    val dhuhr: String? = null,

    @SerialName("Maghrib")
    val maghrib: String? = null,

    @SerialName("Sunrise")
    val sunrise: String? = null,

    @SerialName("Midnight")
    val midnight: String? = null,

    @SerialName("Imsak")
    val imsak: String? = null
)

@Serializable
data class DateResponse(

    @SerialName("readable")
    val readable: String? = null,

    @SerialName("hijri")
    val hijri: CalendarFormatResponse? = null,

    @SerialName("gregorian")
    val gregorian: CalendarFormatResponse? = null,

    @SerialName("timestamp")
    val timestamp: String? = null
)

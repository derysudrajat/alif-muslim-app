package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(

    @field:SerializedName("date")
    val dateResponse: DateResponse? = null,

    @field:SerializedName("meta")
    val metaResponse: MetaResponse? = null,

    @field:SerializedName("timings")
    val timingResponse: TimingResponse? = null
)

data class MetaResponse(

    @field:SerializedName("timezone")
    val timezone: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,
)

data class TimingResponse(

    @field:SerializedName("Sunset")
    val sunset: String? = null,

    @field:SerializedName("Asr")
    val asr: String? = null,

    @field:SerializedName("Isha")
    val isha: String? = null,

    @field:SerializedName("Fajr")
    val fajr: String? = null,

    @field:SerializedName("Dhuhr")
    val dhuhr: String? = null,

    @field:SerializedName("Maghrib")
    val maghrib: String? = null,

    @field:SerializedName("Sunrise")
    val sunrise: String? = null,

    @field:SerializedName("Midnight")
    val midnight: String? = null,

    @field:SerializedName("Imsak")
    val imsak: String? = null
)

data class DateResponse(

    @field:SerializedName("readable")
    val readable: String? = null,

    @field:SerializedName("hijri")
    val hijri: CalendarFormatResponse? = null,

    @field:SerializedName("gregorian")
    val gregorian: CalendarFormatResponse? = null,

    @field:SerializedName("timestamp")
    val timestamp: String? = null
)

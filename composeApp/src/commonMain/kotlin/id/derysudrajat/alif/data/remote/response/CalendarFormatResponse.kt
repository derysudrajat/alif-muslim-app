package id.derysudrajat.alif.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalendarFormatResponse(
    val date: String? = null,

    @SerialName("month")
    val monthResponse: MonthResponse? = null,

    @SerialName("year")
    val year: String? = null,

    @SerialName("format")
    val format: String? = null,

    @SerialName("weekday")
    val weekdayResponse: WeekdayResponse? = null,

    @SerialName("day")
    val day: String? = null,

    @SerialName("holidays")
    val holidays: List<String>? = null,
)


@Serializable
data class MonthResponse(

    @SerialName("number")
    val number: Int? = null,

    @SerialName("en")
    val en: String? = null,

    @SerialName("ar")
    val ar: String? = null
)

@Serializable
data class WeekdayResponse(

    @SerialName("en")
    val en: String? = null,

    @SerialName("ar")
    val ar: String? = null
)

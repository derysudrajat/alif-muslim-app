package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName

data class CalendarFormatResponse(
    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("month")
    val monthResponse: MonthResponse? = null,

    @field:SerializedName("year")
    val year: String? = null,

    @field:SerializedName("format")
    val format: String? = null,

    @field:SerializedName("weekday")
    val weekdayResponse: WeekdayResponse? = null,

    @field:SerializedName("day")
    val day: String? = null,

    @field:SerializedName("holidays")
    val holidays: List<String>? = null,
)


data class WeekdayResponse(

    @field:SerializedName("en")
    val en: String? = null,

    @field:SerializedName("ar")
    val ar: String? = null
)

data class MonthResponse(

    @field:SerializedName("number")
    val number: Int? = null,

    @field:SerializedName("en")
    val en: String? = null,

    @field:SerializedName("ar")
    val ar: String? = null
)
package id.derysudrajat.alif.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrayerScheduleResponse(

    @SerialName("code")
    val code: Int? = null,

    @SerialName("data")
    val data: List<ScheduleResponse>? = null,

    @SerialName("status")
    val status: String? = null
)
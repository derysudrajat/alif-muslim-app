package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName

data class PrayerScheduleResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<ScheduleResponse>? = null,

    @field:SerializedName("status")
    val status: String? = null
)
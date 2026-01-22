package id.derysudrajat.alif.data.remote.response

import id.derysudrajat.alif.domain.model.AyahData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AyahResponse(

    @SerialName("ar")
    val ar: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("nomor")
    val nomor: String? = null,

    @SerialName("tr")
    val tr: String? = null
)

fun List<AyahResponse>.toAyahs(): MutableList<AyahData> {
    val listAyah = mutableListOf<AyahData>()
    this.forEach { listAyah.add(it.toAyah()) }
    return listAyah
}

fun AyahResponse.toAyah(): AyahData {
    return AyahData(
        this.ar ?: "",
        this.id ?: "",
        this.nomor?.toInt() ?: -1,
        this.tr ?: ""
    )
}
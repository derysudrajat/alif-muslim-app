package id.derysudrajat.alif.data.remote.response

import id.derysudrajat.alif.domain.model.SurahData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListSurahResponse(

    @SerialName("surah")
    val listSurahResponse: List<SurahResponse>? = null
)

@Serializable
data class SurahResponse(

    @SerialName("nama")
    val nama: String? = null,

    @SerialName("ayat")
    val ayat: Int? = null,

    @SerialName("urut")
    val urut: String? = null,

    @SerialName("arti")
    val arti: String? = null,

    @SerialName("asma")
    val arabic: String? = null,

    @SerialName("audio")
    val audio: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("nomor")
    val nomor: String? = null
)

fun List<SurahResponse>.toListSurah(): MutableList<SurahData> {
    val listSurah = mutableListOf<SurahData>()
    this.forEach { listSurah.add(it.toSurah()) }
    return listSurah
}


fun SurahResponse.toSurah(): SurahData {
    return SurahData(
        this.nama ?: "",
        this.ayat ?: 0,
        this.urut?.toInt() ?: 0,
        this.arti ?: "",
        this.arabic ?: "",
        this.audio ?: "",
        this.type ?: "-",
        this.nomor?.toInt() ?: 0
    )
}
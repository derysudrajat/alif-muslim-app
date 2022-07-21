package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName
import id.derysudrajat.alif.data.model.Surah

data class ListSurahResponse(

    @field:SerializedName("surah")
    val listSurahResponse: List<SurahResponse>? = null
)

data class SurahResponse(

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("ayat")
    val ayat: Int? = null,

    @field:SerializedName("urut")
    val urut: String? = null,

    @field:SerializedName("arti")
    val arti: String? = null,

    @field:SerializedName("asma")
    val arabic: String? = null,

    @field:SerializedName("audio")
    val audio: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("nomor")
    val nomor: String? = null
)

fun List<SurahResponse>.toListSurah(): MutableList<Surah> {
    val listSurah = mutableListOf<Surah>()
    this.forEach { listSurah.add(it.toSurah()) }
    return listSurah
}


fun SurahResponse.toSurah(): Surah {
    return Surah(
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
package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName
import id.derysudrajat.alif.data.model.Ayah

data class AyahResponse(

    @field:SerializedName("ar")
    val ar: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nomor")
    val nomor: String? = null,

    @field:SerializedName("tr")
    val tr: String? = null
)

fun List<AyahResponse>.toAyahs(): MutableList<Ayah> {
    val listAyah = mutableListOf<Ayah>()
    this.forEach { listAyah.add(it.toAyah()) }
    return listAyah
}

fun AyahResponse.toAyah(): Ayah {
    return Ayah(
        this.ar ?: "",
        this.id ?: "",
        this.nomor?.toInt() ?: -1,
        this.tr ?: ""
    )
}

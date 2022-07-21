package id.derysudrajat.alif.repo.remote.response

import com.google.gson.annotations.SerializedName
import id.derysudrajat.alif.data.model.Juz
import id.derysudrajat.alif.data.model.JuzContainer

data class ListJuzResponse(
    @field:SerializedName("data")
    val data: List<JuzResponse>? = null,
)

data class JuzContainerResponse(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("index")
    val index: String? = null,

    @field:SerializedName("verse")
    val verse: String? = null
)

data class JuzResponse(

    @field:SerializedName("start")
    val start: JuzContainerResponse? = null,

    @field:SerializedName("index")
    val index: String? = null,

    @field:SerializedName("end")
    val end: JuzContainerResponse? = null
)

fun List<JuzResponse>.toListJuz(): MutableList<Juz> {
    val listJuz = mutableListOf<Juz>()
    this.forEach { listJuz.add(it.toJuz()) }
    return listJuz
}

fun JuzResponse.toJuz(): Juz {
    return Juz(
        this.index?.toInt() ?: 0,
        JuzContainer(
            this.start?.name ?: "",
            this.start?.index?.toInt() ?: 0,
            this.start?.verse?.toInt() ?: 0
        ),
        JuzContainer(
            this.end?.name ?: "",
            this.end?.index?.toInt() ?: 0,
            this.end?.verse?.toInt() ?: 0
        )
    )
}

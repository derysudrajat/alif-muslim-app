package id.derysudrajat.alif.data.remote.response

import id.derysudrajat.alif.domain.model.JuzContainerData
import id.derysudrajat.alif.domain.model.JuzData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListJuzResponse(
    @SerialName("data")
    val data: List<JuzResponse>? = null,
)

@Serializable
data class JuzContainerResponse(

    @SerialName("name")
    val name: String? = null,

    @SerialName("index")
    val index: String? = null,

    @SerialName("verse")
    val verse: String? = null
)

@Serializable
data class JuzResponse(

    @SerialName("start")
    val start: JuzContainerResponse? = null,

    @SerialName("index")
    val index: String? = null,

    @SerialName("end")
    val end: JuzContainerResponse? = null
)

fun List<JuzResponse>.toListJuz(): MutableList<JuzData> {
    val listJuz = mutableListOf<JuzData>()
    this.forEach { listJuz.add(it.toJuz()) }
    return listJuz
}

fun JuzResponse.toJuz(): JuzData {
    return JuzData(
        this.index?.toInt() ?: 0,
        JuzContainerData(
            this.start?.name ?: "",
            this.start?.index?.toInt() ?: 0,
            this.start?.verse?.toInt() ?: 0
        ),
        JuzContainerData(
            this.end?.name ?: "",
            this.end?.index?.toInt() ?: 0,
            this.end?.verse?.toInt() ?: 0
        )
    )
}
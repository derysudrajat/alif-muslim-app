package id.derysudrajat.alif.domain.model

data class JuzContainerData(
    val name: String,
    val index: Int,
    val verse: Int
)

data class JuzData(
    val index: Int,
    val start: JuzContainerData,
    val end: JuzContainerData
)
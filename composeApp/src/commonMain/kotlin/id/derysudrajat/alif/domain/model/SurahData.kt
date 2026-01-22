package id.derysudrajat.alif.domain.model

data class SurahData(
    val name: String,
    val ayahs: Int,
    val number: Int,
    val mean: String,
    val arabic: String,
    val audioUrl: String,
    val type: String,
    val index: Int
) {
    companion object {
        val Empty = SurahData(
            name = "",
            ayahs = 0,
            number = 0,
            mean = "",
            arabic = "",
            audioUrl = "",
            type = "",
            index = 0
        )
    }
}
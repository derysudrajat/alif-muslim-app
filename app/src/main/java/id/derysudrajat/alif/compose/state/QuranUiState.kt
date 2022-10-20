package id.derysudrajat.alif.compose.state

import id.derysudrajat.alif.data.model.Juz
import id.derysudrajat.alif.data.model.Surah

data class QuranUiState(
    var listSurah: List<Surah> = emptyList(),
    var listJuz: List<Juz> = emptyList(),
    var onBack: () -> Unit = {}
)
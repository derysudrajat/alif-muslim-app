package id.derysudrajat.alif.compose.page

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.compose.state.QuranUiState

// TODO 13: create composable for QuranOnly content with Param,
//  quranUiState: QuranUiState
@Composable
fun QuranOnly(
    quranUiState: QuranUiState
) {

}

// TODO 14: create composable for QuranAndSurah content with Param,
//  quranUiState: QuranUiState,
//  surahUiState: SurahUiState,
//  navType: QuranNavType,
//  onGetAyahFromSurah : (index: Int) -> Unit,
//  onSetSurah: (surah: Surah) -> Unit,
//  onInitMedia: (surah: Surah) -> Unit
@Composable
fun QuranAndSurah() {

}

// TODO 15: create preview for PreviewQuranAndSurah content
@Preview(showBackground = true, widthDp = 700)
@Composable
private fun PreviewQuranAndSurah() {

}
package id.derysudrajat.alif.ui.quran

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.compose.page.QuranContent
import id.derysudrajat.alif.compose.page.QuranPage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.data.model.Surah

@AndroidEntryPoint
class QuranActivity : ComponentActivity() {

    private val model: QuranViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.setBack { onBack() }
        model.getListSurah(this)
        model.getListJuz(this)

        setContent {
            AlifTheme {
                val uiState = model.uiState.collectAsState().value
                QuranPage(uiState.onBack) {
                    QuranContent(
                        modifier = it.weight(1f), uiState,
                        this@QuranActivity::toSurahActivity
                    )
                }
            }
        }
    }

    private fun onBack() = finish()

    private fun toSurahActivity(surah: Surah) {
        startActivity(Intent(this, SurahActivity::class.java).apply {
            putExtra(SurahActivity.EXTRA_SURAH, surah)
        })
    }
}

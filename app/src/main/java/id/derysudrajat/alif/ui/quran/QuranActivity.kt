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

// TODO 2 : Change ComponentActivity to BaseAudioSurahActivity
@AndroidEntryPoint
class QuranActivity : ComponentActivity() {

    private val model: QuranViewModel by viewModels()

    // TODO 4: add OpIn Annotation Experimental Material
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.setBack { onBack() }
        model.getListSurah(this)
        model.getListJuz(this)
        // TODO 16: call setCallBack from parent surahViewModel

        // TODO 17: call parent function initMediaPlayer using model uiState listSurah index 0


        setContent {
            AlifTheme {
                // TODO 3:  put calculate windows size class into variable windowSize

                val uiState = model.uiState.collectAsState().value
                // TODO 18: collectAsState a value of uiState from surahViewModel

                // TODO 19: handle if surah from surahUiState is [Surah.empty]
                // TODO 19.1: call getAyahFromSurah from surahViewModel inside if and set with index variable of listSurah index-0 from uiState
                // TODO 19.2: call setSurah from surahViewModel inside if and set with listSurah variable index-0 from uiState

                // TODO 20: delete this QuranPage content below
                QuranPage(uiState.onBack) {
                    QuranContent(
                        modifier = it.weight(1f), uiState,
                        this@QuranActivity::toSurahActivity
                    )
                }
                // TODO 21: handle widthSizeClass from windowSize
                // TODO 21.1: when widthSizeClass is Compact set content to QuranOnly with uiState as param
                // TODO 21.2: when widthSizeClass is Medium and Expanded set Content to QuranAndSurah with
                //  uiState, surahUiState,
                //  navType -> when widthSizeClass Medium set to NavRail else NavDrawer,
                //  call function getAyahFromSurah using method reference (::) from surahViewModel,
                //  call function setSurah using method reference (::) from surahViewModel,
                //  and call parent function initMediaPlayer using method reference (::) as Param
            }
        }
    }

    private fun onBack() = finish()

    // TODO 22: delete this function
    private fun toSurahActivity(surah: Surah) {
        startActivity(Intent(this, SurahActivity::class.java).apply {
            putExtra(SurahActivity.EXTRA_SURAH, surah)
        })
    }
}

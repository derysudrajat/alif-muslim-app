package id.derysudrajat.alif.ui.quran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.derysudrajat.alif.compose.QuranPage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme

@AndroidEntryPoint
class QuranActivity : ComponentActivity() {

    private val model: QuranViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                QuranPage(model.listSurah, model.listJuz) {
                    finish()
                }
            }
        }
        model.getListSurah(this)
        model.getListJuz(this)
    }
}

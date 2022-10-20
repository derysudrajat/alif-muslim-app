package id.derysudrajat.alif.compose.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.state.SurahUiState
import id.derysudrajat.alif.compose.ui.components.BaseTopBar
import id.derysudrajat.alif.compose.ui.components.ItemAudio
import id.derysudrajat.alif.compose.ui.components.ItemAyah
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeadingXLarge
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.data.model.Surah

@Composable
fun SurahPage(
    surahUiState: SurahUiState
) {
    Scaffold(modifier = Modifier.padding(16.dp), topBar = {
        BaseTopBar(title = surahUiState.surah.name, surahUiState.onBack)
    }) { paddingValues ->
        SurahContent(paddingValues = paddingValues, surahUiState = surahUiState)
    }
}

@Composable
fun SurahContent(
    modifier: Modifier = Modifier,
    surahUiState: SurahUiState,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues
    ) {
        item { ItemHeaderSurah(surahUiState.surah) }
        item {
            ItemAudio(
                surahUiState.audioProgress, surahUiState.isFinish,
                surahUiState.currentDurationPosition,
                surahUiState.onStart, surahUiState.onPause
            )
        }
        items(surahUiState.listAyah) {
            if (surahUiState.surah.index != 1) ItemAyah(it)
            else {
                if (it.no != 1) ItemAyah(it)
            }
        }
    }
}


@Composable
fun ItemHeaderSurah(
    surah: Surah
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Primary,
        shape = RoundedCornerShape(16.dp)
    ) {

        Box {
            Image(
                painterResource(id = R.drawable.ic_bg_surah),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                val (textName, textTranslate, divider, textDesc, textBismillah) = createRefs()

                TextHeadingXLarge(
                    modifier = Modifier.constrainAs(textName) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, text = surah.name, textColor = White
                )
                TextBody(modifier = Modifier.constrainAs(textTranslate) {
                    top.linkTo(textName.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, text = surah.mean, textColor = White)

                Box(modifier = Modifier
                    .width(200.dp)
                    .height(1.dp)
                    .background(White)
                    .constrainAs(divider) {
                        top.linkTo(textTranslate.bottom, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                TextBody(
                    modifier = Modifier.constrainAs(textDesc) {
                        top.linkTo(divider.bottom, margin = 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }, text = "${surah.type} - ${surah.ayahs} Ayat", textColor = White
                )

                Image(
                    modifier = Modifier.constrainAs(textBismillah) {
                        top.linkTo(textDesc.bottom, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.value(250.dp)
                        height = Dimension.wrapContent
                    },
                    painter = painterResource(id = R.drawable.ic_bismillah_white),
                    contentDescription = ""
                )

            }
        }
    }
}

@Preview
@Composable
private fun PreviewItemHeaderSurah() {
    ItemHeaderSurah(
        Surah(
            "An-Naas",
            6,
            21,
            "Manusia",
            "الناس",
            "http://ia802609.us.archive.org/13/items/quraninindonesia/114AnNaas.mp3",
            "Makkiyah",
            114
        )
    )
}
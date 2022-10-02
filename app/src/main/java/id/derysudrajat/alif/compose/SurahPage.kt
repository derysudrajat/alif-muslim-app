package id.derysudrajat.alif.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import id.derysudrajat.alif.compose.ui.components.BaseTopBar
import id.derysudrajat.alif.compose.ui.components.ItemAudio
import id.derysudrajat.alif.compose.ui.components.ItemAyah
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeadingXLarge
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.data.model.Ayah
import id.derysudrajat.alif.data.model.Surah

@Composable
fun SurahPage(
    surah: Surah,
    listAyah: List<Ayah>,
    audioProgress: Float,
    isFinish: Boolean?,
    onBack: () -> Unit,
    onStart: () -> Unit,
    onPause: () -> Unit
) {
    Scaffold(modifier = Modifier.padding(16.dp), topBar = {
        BaseTopBar(title = surah.name, onBack)
    }) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            item { ItemHeaderSurah(surah) }
            item { ItemAudio(audioProgress, isFinish, onStart, onPause) }
            items(listAyah) {
                if (surah.index != 1) ItemAyah(it)
                else {
                    if (it.no != 1) ItemAyah(it)
                }
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
fun PreviewItemHeaderSurah() {
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
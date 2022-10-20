package id.derysudrajat.alif.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.compose.ui.foundation.text.TextArabic
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextBodySmall
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeading
import id.derysudrajat.alif.compose.ui.foundation.text.TextSubtitle
import id.derysudrajat.alif.compose.ui.theme.AlifThemes
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.data.model.Surah

@Composable
fun ItemSurah(surah: Surah, onClick: (Surah) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .clickable {
                onClick(surah)
            }
            .padding(16.dp)
    ) {
        val (noSurah, title, desc, mean, arabic) = createRefs()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(26.dp))
                .background(AlifThemes.Colors.primary)
                .constrainAs(noSurah) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.value(52.dp)
                    height = Dimension.ratio("1:1")
                }, contentAlignment = Alignment.Center
        ) {
            TextSubtitle(
                modifier = Modifier.padding(8.dp),
                text = "${surah.index}",
                textColor = White
            )
        }

        TextHeading(modifier = Modifier.constrainAs(title) {
            top.linkTo(noSurah.top, margin = 8.dp)
            start.linkTo(noSurah.end, margin = 16.dp)
            end.linkTo(if (surah.arabic.isNotBlank()) arabic.start else parent.end, margin = 8.dp)
            width = Dimension.fillToConstraints
        }, text = surah.name)

        TextBodySmall(
            modifier = Modifier.constrainAs(desc) {
                top.linkTo(if (surah.mean.isNotBlank()) mean.bottom else title.bottom)
                start.linkTo(title.start)
                end.linkTo(
                    if (surah.arabic.isNotBlank()) arabic.start else parent.end,
                    margin = 8.dp
                )
                width = Dimension.fillToConstraints
            }, text = "${surah.type} - ${surah.ayahs} ayahs", textColor = Color.Gray
        )

        if (surah.mean.isNotBlank()) TextBody(modifier = Modifier.constrainAs(mean) {
            top.linkTo(title.bottom)
            start.linkTo(title.start)
            end.linkTo(arabic.start, margin = 8.dp)
            width = Dimension.fillToConstraints
        }, text = "(${surah.mean})", textColor = Color.Gray)

        if (surah.arabic.isNotBlank()) TextArabic(
            modifier = Modifier.constrainAs(arabic) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            text = surah.arabic, fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemSurah() {
    ItemSurah(
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
    ) {

    }
}

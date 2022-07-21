package id.derysudrajat.alif.compose.ui.components

import android.os.Build
import android.text.Html
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.compose.ui.foundation.text.TextArabic
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.theme.AlifThemes
import id.derysudrajat.alif.compose.ui.theme.Gray
import id.derysudrajat.alif.compose.ui.theme.Primary10
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.data.model.Ayah

@Composable
fun ItemAyah(
    dataAyah: Ayah
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        val (noAyah, ayah, roman, translate, divider) = createRefs()
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Primary10)
                .constrainAs(noAyah) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = Dimension.value(48.dp)
                    height = Dimension.ratio("1:1")
                }, contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(AlifThemes.Colors.primary), contentAlignment = Alignment.Center
            ) {
                TextBody(
                    modifier = Modifier.padding(8.dp), text = "${dataAyah.no}", textColor = White
                )
            }
        }


        val bismillah = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ "
        val printTableAyah =
            if (dataAyah.no == 1 && dataAyah.arabic.contains(bismillah)) dataAyah.arabic.split(
                bismillah
            ).last()
            else dataAyah.arabic

        TextArabic(
            modifier = Modifier.constrainAs(ayah) {
                top.linkTo(noAyah.bottom, margin = 8.dp)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
            }, text = printTableAyah, textAlign = TextAlign.End
        )

        val romanText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
            dataAyah.roman, Html.FROM_HTML_MODE_COMPACT
        )
        else Html.fromHtml(dataAyah.roman)

        TextBody(
            modifier = Modifier.constrainAs(roman) {
                top.linkTo(ayah.bottom, margin = 8.dp)
                start.linkTo(noAyah.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, text = romanText.toString(), textColor = Color(0xFF38A188)
        )

        TextBody(
            modifier = Modifier.constrainAs(translate) {
                top.linkTo(roman.bottom, margin = 8.dp)
                start.linkTo(noAyah.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }, text = dataAyah.translate
        )

        Box(modifier = Modifier
            .height(0.2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(1.dp))
            .background(Gray)
            .constrainAs(divider) {
                top.linkTo(translate.bottom, margin = 16.dp)
            })
    }
}
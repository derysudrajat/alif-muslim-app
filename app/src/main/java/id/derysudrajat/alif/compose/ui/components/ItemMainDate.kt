package id.derysudrajat.alif.compose.ui.components


import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.firebase.Timestamp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeadingLarge
import id.derysudrajat.alif.data.model.DateSchedule
import id.derysudrajat.alif.ui.compass.CompassActivity
import id.derysudrajat.alif.ui.quran.QuranActivity
import id.derysudrajat.alif.utils.TimeUtils.fullDate

@Composable
fun ItemMainDate(
    hijriDate: DateSchedule
) {
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {
        val (iconQuran, title, iconQibla) = createRefs()
        ActionButton(modifier = Modifier.constrainAs(iconQuran) {
            start.linkTo(parent.start, margin = 8.dp)
            bottom.linkTo(parent.bottom)
            top.linkTo(parent.top)
        }, icon = R.drawable.ic_quran) {
            context.startActivity(Intent(context, QuranActivity::class.java))
        }
        Column(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(iconQuran.end)
                end.linkTo(iconQibla.start)
                width = Dimension.fillToConstraints
            }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            TextHeadingLarge(text = Timestamp.now().fullDate)
            TextBody(text = buildString {
                append("${hijriDate.day} ${hijriDate.monthDesignation} ${hijriDate.year} ${hijriDate.yearDesignation}")
            })
            Spacer(modifier = Modifier.height(32.dp))
        }

        ActionButton(modifier = Modifier.constrainAs(iconQibla) {
            end.linkTo(parent.end, margin = 8.dp)
            bottom.linkTo(parent.bottom)
            top.linkTo(parent.top)
        }, icon = R.drawable.ic_compas) {
            context.startActivity(Intent(context, CompassActivity::class.java))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemMainDate() {
    ItemMainDate(dummyCalendar)
}
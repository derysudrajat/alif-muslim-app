package id.derysudrajat.alif.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.Primary10
import id.derysudrajat.alif.compose.ui.theme.Secondary
import id.derysudrajat.alif.data.model.DateSchedule

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemCalendar(
    hijriDate: DateSchedule, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = RoundedCornerShape(16.dp),
        onClick = { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp)
        ) {
            val (icon, titleText, event, date, arrow) = createRefs()
            Box(modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clip(RoundedCornerShape(8.dp))
                .background(Primary10)) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_activity),
                    contentDescription = ""
                )
            }
            TextTitle(
                modifier = Modifier.constrainAs(titleText) {
                    top.linkTo(icon.top)
                    start.linkTo(icon.end, margin = 16.dp)
                    bottom.linkTo(icon.bottom)
                }, text = "Calendar"
            )
            TextBody(modifier = Modifier.constrainAs(event) {
                top.linkTo(titleText.bottom, margin = 8.dp)
                start.linkTo(titleText.start)
            }, text = buildString {
                if (hijriDate.holidays.isEmpty()) append("No Event Today")
                else hijriDate.holidays.forEachIndexed { index, s ->
                    append(s)
                    if (index != hijriDate.holidays.size - 1) append("\n")
                }
            }, textColor = Primary)

            TextBody(modifier = Modifier.constrainAs(date) {
                top.linkTo(event.bottom)
                start.linkTo(titleText.start)
            }, text = buildString {
                append("${hijriDate.day} ${hijriDate.monthDesignation} ${hijriDate.year} ${hijriDate.yearDesignation}")
            })
            Image(
                modifier = Modifier.constrainAs(arrow) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Secondary)
            )
        }
    }
}

val dummyCalendar = DateSchedule(
    1,
    7,
    "Ramadhan",
    1443,
    "AH",
    "Sunday",
    "01-09-1443",
    listOf("1st Day of Ramadhan")
)

@Preview
@Composable
private fun PreviewItemCalendar() {
    ItemCalendar(dummyCalendar) {}
}
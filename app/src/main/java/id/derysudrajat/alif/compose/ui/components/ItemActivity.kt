package id.derysudrajat.alif.compose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.firebase.Timestamp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextBodySmall
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.Black60
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.data.model.ProgressTask
import id.derysudrajat.alif.utils.TimeUtils.hourMinutes
import java.util.Date

@Composable
fun ItemActivity(
    progressTask: ProgressTask
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (titleRef, timeIconRef, timeTextRef, checkRef) = createRefs()
            TextBody(
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
                text = progressTask.title
            )
            Image(
                modifier = Modifier.constrainAs(timeIconRef) {
                    top.linkTo(titleRef.bottom, 8.dp)
                    start.linkTo(parent.start)
                },
                colorFilter = ColorFilter.tint(Black60),
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "Time Icon"
            )
            TextBodySmall(
                modifier = Modifier.constrainAs(timeTextRef) {
                    top.linkTo(timeIconRef.top)
                    start.linkTo(timeIconRef.end, 8.dp)
                    bottom.linkTo(timeIconRef.bottom)
                },
                text = Timestamp(Date(progressTask.date)).hourMinutes
            )
            Box(
                modifier = Modifier
                    .clickable { }
                    .constrainAs(checkRef) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.ratio("1:1")
                    }
            ) {
                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp),
                    colorFilter = ColorFilter.tint(
                        if (progressTask.isCheck) Primary else Black60
                    ),
                    painter = painterResource(
                        id =
                        if (progressTask.isCheck) R.drawable.ic_check_fill else R.drawable.ic_check_outline
                    ),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewItemActivityNight() {
    AlifTheme {
        ItemActivity(
            ProgressTask(
                1239281309829,
                "Sholat Dzuhur",
                123123213,
                "0",
                true
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemActivityDay() {
    AlifTheme {
        ItemActivity(
            ProgressTask(
                1239281309829,
                "Sholat Dzuhur",
                123123213,
                "0",
                false
            )
        )
    }
}
package id.derysudrajat.alif.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.Gray
import id.derysudrajat.alif.compose.ui.theme.Primary10
import id.derysudrajat.alif.compose.ui.theme.Secondary
import id.derysudrajat.alif.data.model.ProgressTask
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ItemProgressActivity(
    listOfTask: List<ProgressTask>,
    onClick: () -> Unit
) {
    val taskProgress = listOfTask.filter { it.isCheck }.size.toDouble()
    val percentage = if (taskProgress != 0.0) (taskProgress / listOfTask.size.toDouble()) else 0.0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), shape = RoundedCornerShape(16.dp),
        onClick = { onClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp)
        ) {
            val (icon, titleText, textAll, progress, textProgress, textActivityRemains) = createRefs()
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
                }, text = "Activity"
            )
            TextBody(
                modifier = Modifier.constrainAs(textAll) {
                    end.linkTo(parent.end)
                    top.linkTo(titleText.top)
                    bottom.linkTo(titleText.bottom)
                }, text = "All(${listOfTask.size})"
            )
            LinearProgressIndicator(
                progress = percentage.toFloat(),
                modifier = Modifier
                    .height(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(progress) {
                        top.linkTo(icon.bottom, margin = 8.dp)
                        start.linkTo(titleText.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                backgroundColor = Gray,
                color = Secondary
            )
            TextBody(modifier = Modifier.constrainAs(textProgress) {
                top.linkTo(progress.bottom, margin = 8.dp)
                start.linkTo(progress.start)
            }, text = buildString {
                if (listOfTask.isNotEmpty()) {
                    append("Progress: ")
                    if ((percentage * 100) % 2.0 == 0.0) append((percentage * 100).toInt())
                    else append(DecimalFormat("##.##").format((percentage * 100).toInt()))
                    append("%")
                } else append("No Activity yet, go add it")
            })
            TextBody(modifier = Modifier.constrainAs(textActivityRemains) {
                top.linkTo(progress.bottom, margin = 8.dp)
                end.linkTo(progress.end)
            }, text = buildString {
                if (listOfTask.isNotEmpty()) {
                    val remains = listOfTask.size - taskProgress.toInt()
                    append(remains)
                    append(" Activity Remain")
                    if (remains > 1) append("s")
                }
            })
        }
    }
}

@Preview
@Composable
private fun PreviewItemProgressActivity() {
    ItemProgressActivity(dummyListProgressTask) {}
}

val dummyListProgressTask = listOf(
    ProgressTask(0L, "", 0L, "", false),
    ProgressTask(0L, "", 0L, "", true),
    ProgressTask(0L, "", 0L, "", true),
    ProgressTask(0L, "", 0L, "", true),
    ProgressTask(0L, "", 0L, "", true),
    ProgressTask(0L, "", 0L, "", false),
    ProgressTask(0L, "", 0L, "", false),
)

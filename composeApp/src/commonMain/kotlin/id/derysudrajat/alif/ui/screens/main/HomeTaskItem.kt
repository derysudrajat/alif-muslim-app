package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_task
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeTaskItem() {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = AppColor.Background
        )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            val (
                iconRef, headerRef, progressRef, captionRef
            ) = createRefs()

            Box(
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .background(AppColor.Primary.Light.copy(0.3f))
                    .constrainAs(iconRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            ) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(Res.drawable.ic_task),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppColor.Primary.Main)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().constrainAs(headerRef) {
                    top.linkTo(iconRef.top)
                    start.linkTo(iconRef.end, 16.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(iconRef.bottom)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColor.Text
                )
                Spacer(Modifier.weight(1f))
                TextButton(
                    onClick = {}, colors = ButtonDefaults.textButtonColors(
                        contentColor = AppColor.Primary.Main,
                    )
                ) {
                    Text(text = "All (6)", color = AppColor.Text)
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth().constrainAs(progressRef) {
                    top.linkTo(headerRef.bottom, 8.dp)
                    start.linkTo(headerRef.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LinearWavyProgressIndicator(
                    modifier = Modifier.fillMaxWidth().height(16.dp),
                    color = AppColor.Secondary.Main,
                    trackStroke = Stroke(
                        width =
                            with(LocalDensity.current) {
                                8.dp.toPx()
                            },
                        cap = StrokeCap.Round,
                    ),
                    stroke = Stroke(
                        width =
                            with(LocalDensity.current) {
                                8.dp.toPx()
                            },
                        cap = StrokeCap.Round,
                    ),
                    stopSize = 8.dp,
                    trackColor = AppColor.Gray.copy(0.5f),
                    progress = { 0.5f }
                )
                Row {
                    Text(
                        text = buildAnnotatedString {
                            append("Progress: ")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = AppColor.Text
                                )
                            ) {
                                append("16%")
                            }
                        },
                        color = AppColor.Text.copy(0.6f),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "5 Remains",
                        color = AppColor.Text.copy(0.6f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewHomeTaskItem() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth().background(AppColor.Background).padding(16.dp)) {
            HomeTaskItem()
        }
    }
}
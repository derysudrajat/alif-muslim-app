package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.bg_schedule
import alifmuslimapp.composeapp.generated.resources.ic_location
import alifmuslimapp.composeapp.generated.resources.ic_sound_off
import alifmuslimapp.composeapp.generated.resources.ic_sound_on
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.ui.components.LottieAnimation
import id.derysudrajat.alif.ui.themes.AppColor
import org.jetbrains.compose.resources.painterResource


@Composable
fun ScheduleTimeCard() {
    val (isPlaying, setPlaying) = remember { mutableStateOf(true) }
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1.8f)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (backgroundRef, contentRef) = createRefs()
            Image(
                modifier = Modifier.constrainAs(backgroundRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                painter = painterResource(Res.drawable.bg_schedule),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.constrainAs(contentRef) {
                    top.linkTo(parent.top, 16.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = AppColor.Primary.Light
                        ),
                        onClick = { setPlaying(!isPlaying) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(
                                if (isPlaying) Res.drawable.ic_sound_on
                                else Res.drawable.ic_sound_off
                            ),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = if (isPlaying) "On" else "Off",
                        color = AppColor.White.Main,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("4h 58m 9s")
                            }
                            append(" to ")
                            append("Imsak")
                        },
                        color = AppColor.White.Main,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Next Prayer Time",
                            color = AppColor.White.Main
                        )
                        Text(
                            text = "04:48 (WIB)",
                            color = AppColor.White.Main,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                modifier = Modifier.size(14.dp),
                                painter = painterResource(Res.drawable.ic_location),
                                contentDescription = null
                            )
                            Text(
                                text = "Bendungan Hilir, Tanah Abang, Jakarta Pusat, DKI Jakarta",
                                color = AppColor.White.Main,
                                fontSize = 10.sp,
                                maxLines = 2,
                                lineHeight = 10.sp,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    LottieAnimation(
                        modifier = Modifier.weight(1f),
                        resourcePath = "files/anim_day.json"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewScheduleTimeCard() {
    Box(
        modifier = Modifier.padding(32.dp),
    ) {
        ScheduleTimeCard()
    }
}
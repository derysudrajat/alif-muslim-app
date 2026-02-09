package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.bg_schedule
import alifmuslimapp.composeapp.generated.resources.ic_location
import alifmuslimapp.composeapp.generated.resources.ic_sound_off
import alifmuslimapp.composeapp.generated.resources.ic_sound_on
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.domain.model.PrayerData
import id.derysudrajat.alif.domain.model.PrayerStatus
import id.derysudrajat.alif.domain.model.TimingSchedule
import id.derysudrajat.alif.domain.model.getPrayerStatus
import id.derysudrajat.alif.domain.model.getScheduleName
import id.derysudrajat.alif.domain.model.getTimeUntil
import id.derysudrajat.alif.domain.model.parseToMinutes
import id.derysudrajat.alif.domain.model.toList
import id.derysudrajat.alif.ui.components.LottieAnimation
import id.derysudrajat.alif.ui.themes.AppColor
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock


@Composable
fun ScheduleTimeCard(
    timingSchedule: TimingSchedule,
    onNearestScheduleChange: (PrayerData) -> Unit
) {
    val (isPlaying, setPlaying) = remember { mutableStateOf(true) }
    val (nearSchedule, setNearSchedule) = remember { mutableStateOf(PrayerData.Empty) }
    val (currentLocalTime, setCurrentLocalTime) = remember {
        val timeZone = TimeZone.currentSystemDefault()
        mutableStateOf(Clock.System.now().toLocalDateTime(timeZone))
    }

    if (timingSchedule != TimingSchedule.Empty) RealTimeClock { time ->
        val currentMinutes = (time.hour * 60) + time.minute
        setCurrentLocalTime(time)
        timingSchedule.toList().minByOrNull {
            val pMin = parseToMinutes(it.time)
            val forwardDiff = (pMin - currentMinutes + 1440) % 1440
            forwardDiff
        }.let {
            if (it != nearSchedule) {
                setNearSchedule(it ?: PrayerData.Empty)
                onNearestScheduleChange(it ?: PrayerData.Empty)
            }
        }
    }



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
                            val currentMinutes =
                                (currentLocalTime.hour * 60) + currentLocalTime.minute
                            try {
                                val status =
                                    getPrayerStatus(timingSchedule.toList(), currentMinutes)
                                when (status) {
                                    is PrayerStatus.ItsTime -> {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("It's Time")
                                        }
                                        append(" to ")
                                        append(timingSchedule.getScheduleName(nearSchedule))
                                    }

                                    is PrayerStatus.Next -> {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(
                                                try {
                                                    getTimeUntil(
                                                        nearSchedule.time,
                                                        currentLocalTime
                                                    )
                                                } catch (e: Exception) {
                                                    "--:--:--"
                                                }
                                            )
                                        }
                                        append(" to ")
                                        append(timingSchedule.getScheduleName(nearSchedule))
                                    }

                                    is PrayerStatus.Prepare -> {
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append("Prepare")
                                        }
                                        append(" to ")
                                        append(timingSchedule.getScheduleName(nearSchedule))
                                        append(" in \n")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(
                                                try {
                                                    getTimeUntil(
                                                        nearSchedule.time,
                                                        currentLocalTime
                                                    )
                                                } catch (e: Exception) {
                                                    "--:--:--"
                                                }
                                            )
                                        }
                                    }
                                }
                            } catch (e: Exception) {
                                append("--:--:--")
                            }
                        },
                        textAlign = TextAlign.End,
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
                            text = nearSchedule.time,
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

@Composable
fun RealTimeClock(
    onTimeChanged: (LocalDateTime) -> Unit
) {
    var currentTime by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Clock.System.now()
            val timeZone = TimeZone.currentSystemDefault()
            val localTime = currentTime.toLocalDateTime(timeZone)
            onTimeChanged(localTime)
            delay(1000)
        }
    }
}

@Preview
@Composable
private fun PreviewClock() {
    Box(modifier = Modifier.fillMaxWidth().background(AppColor.Background).padding(32.dp)) {
        RealTimeClock {

        }
    }
}

@Preview
@Composable
private fun PreviewScheduleTimeCard() {
    Box(
        modifier = Modifier.padding(32.dp),
    ) {
        ScheduleTimeCard(
            TimingSchedule.Empty.copy(
                imsak = PrayerData.Empty.copy(
                    time = "04:45 (WIB)",
                    isReminded = false,
                ),
                fajr = PrayerData.Empty.copy(
                    time = "04:55 (WIB)",
                    isReminded = false,
                ),
                sunrise = PrayerData.Empty.copy(
                    time = "05:55 (WIB)",
                    isReminded = false,
                ),
                dhuhr = PrayerData.Empty.copy(
                    time = "12:06 (WIB)",
                    isReminded = false,
                ),
                asr = PrayerData.Empty.copy(
                    time = "15:26 (WIB)",
                    isReminded = false,
                ),
                maghrib = PrayerData.Empty.copy(
                    time = "18:17 (WIB)",
                    isReminded = false,
                ),
                isha = PrayerData.Empty.copy(
                    time = "19:17 (WIB)",
                    isReminded = false,
                ),
            ),
            onNearestScheduleChange = {}
        )
    }
}
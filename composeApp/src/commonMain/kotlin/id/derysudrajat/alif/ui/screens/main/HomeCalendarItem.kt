package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_calendar
import alifmuslimapp.composeapp.generated.resources.icon_arrow_right
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.jetbrains.compose.resources.painterResource


@Composable
fun HomeCalendarItem(
    onClick: () -> Unit
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = AppColor.Background
        )
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            val (
                iconRef,
                titleRef,
                eventRef
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
                    painter = painterResource(Res.drawable.ic_calendar),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppColor.Primary.Main)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().constrainAs(titleRef) {
                    top.linkTo(iconRef.top)
                    start.linkTo(iconRef.end, 16.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(iconRef.bottom)
                    width = Dimension.fillToConstraints
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Islamic Calendar",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColor.Text
                )
                IconButton(onClick = onClick) {
                    Icon(
                        painter = painterResource(Res.drawable.icon_arrow_right),
                        contentDescription = null,
                        tint = AppColor.Text
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth().constrainAs(eventRef) {
                    top.linkTo(iconRef.bottom, 16.dp)
                    start.linkTo(titleRef.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                Text(text = "No Event Today", color = AppColor.Primary.Main)
                Text(
                    text = "22 Dzhul Hijjah 1443 AH",
                    color = AppColor.Text.copy(0.8f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewHomeCalendarItem() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxWidth().background(AppColor.Background).padding(16.dp)) {
            HomeCalendarItem(
                onClick = {}
            )
        }
    }
}
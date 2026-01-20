package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_sound_on
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScheduleItem(
    isActive: Boolean = false
) {
    OutlinedCard(
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (isActive) AppColor.Primary.Light.copy(0.3f) else AppColor.Black.Main_10
        ),
        border = if (isActive) BorderStroke(
            2.dp,
            color = AppColor.Primary.Main
        ) else CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Dzuhur",
                color = AppColor.Text,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "17:46 (WIB)",
                color = AppColor.Text,
            )
            Image(
                painter = painterResource(Res.drawable.ic_sound_on),
                contentDescription = null,
                colorFilter = ColorFilter.tint(AppColor.Primary.Main)
            )
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewScheduleItem() {
    MaterialTheme {
        Box(modifier = Modifier.background(AppColor.Background).padding(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ScheduleItem(true)
                ScheduleItem()
            }
        }
    }
}
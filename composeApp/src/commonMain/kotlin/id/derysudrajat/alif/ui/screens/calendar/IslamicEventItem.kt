package id.derysudrajat.alif.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground

@Composable
fun IslamicEventItem() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(48.dp)
                .clip(CircleShape)
                .background(AppColor.Primary.Light.copy(0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "21", color = AppColor.Primary.Main,
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "1st Day of Ramadhan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AppColor.Text
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "1 Ramadhan 1442 H",
                style = MaterialTheme.typography.bodySmall,
                color = AppColor.Text.copy(0.6f)
            )
        }
        Text(
            text = "Aug, 2026",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = AppColor.Text
        )
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewIslamicEventItem() {
    Box(modifier = Modifier.background(AppColor.Background)) {
        IslamicEventItem()
    }
}
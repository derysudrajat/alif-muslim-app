package id.derysudrajat.alif.ui.screens.main

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_compas
import alifmuslimapp.composeapp.generated.resources.ic_quran
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeaderHome() {
    Row(
        modifier = Modifier.fillMaxWidth().background(AppColor.Background)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(56.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColor.Gray.copy(0.4f)
            ),
            onClick = {}
        ) {
            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_quran),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppColor.Text.copy(0.6f))
                )
            }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Fri, 22 July 2022",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = AppColor.Text,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Text(
                text = "22 Dhu al-Hijjah 1443 AH",
                maxLines = 1,
                color = AppColor.Text.copy(0.6f),
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Card(
            modifier = Modifier.size(56.dp),
            colors = CardDefaults.cardColors(
                containerColor = AppColor.Gray.copy(0.4f)
            ),
            onClick = {}
        ) {
            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.ic_compas),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(AppColor.Text.copy(0.6f))
                )
            }
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewHeaderHome() {
    MaterialTheme {
        HeaderHome()
    }
}
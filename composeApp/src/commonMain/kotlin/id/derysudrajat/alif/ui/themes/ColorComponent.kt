package id.derysudrajat.alif.ui.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground

private val colorsComponentItem = mapOf(
    AppColor.Primary.Main to "AppColor.Primary.Main",
    AppColor.Primary.Dark to "AppColor.Primary.Dark",
    AppColor.Primary.Light to "AppColor.Primary.Light",
    AppColor.Primary.Transparent to "AppColor.Primary.Transparent",
    AppColor.Secondary.Main to "AppColor.Secondary.Main",
    AppColor.Secondary.Dark to "AppColor.Secondary.Dark",
    AppColor.Secondary.Light to "AppColor.Secondary.Light",
    AppColor.Black.Main to "AppColor.Black.Main",
    AppColor.Black.Main_60 to "AppColor.Black.Main_60",
    AppColor.Black.Main_10 to "AppColor.Black.Main_10",
    AppColor.White.Main to "AppColor.White.Main",
    AppColor.White.Main_20 to "AppColor.White.Main_20",
    AppColor.White.Main_10 to "AppColor.White.Main_10",
)

@Composable
private fun ColorItem(color: Color, name: String, textColor: Color = Color.Black) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(name, color = textColor)
    }
}
@Preview
@Composable
fun ColorComponent() {
    MaterialTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color.White),
        ) {
            items(colorsComponentItem.keys.toList()) { color ->
                ColorItem(color, colorsComponentItem.getValue(color))
            }
        }
    }
}




@PreviewLightDarkWithBackground
@Composable
private fun PreviewDynamicColors(){
    MaterialTheme {
        Column(
            Modifier.background(MaterialTheme.colorScheme.surface)
        ) {
            ColorItem(AppColor.Background, "AppColor.Background", AppColor.Text)
            ColorItem(AppColor.Text, "AppColor.Text", AppColor.Background)
        }
    }
}
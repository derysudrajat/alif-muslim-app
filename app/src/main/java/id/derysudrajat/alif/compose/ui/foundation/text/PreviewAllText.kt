package id.derysudrajat.alif.compose.ui.foundation.text

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.compose.ui.theme.AlifTheme

@Preview(showBackground = true)
@Composable
private fun PreviewAllText() {
    AlifTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextHeadingXLarge(text = "TextHeadingXLarge")
            TextHeadingLarge(text = "TextHeadingLarge")
            TextHeading(text = "TextHeading")
            TextTitle(text = "TextTitle")
            TextTitleSmall(text = "TextTitleSmall")
            TextSubtitle(text = "TextSubtitle")
            TextBody(text = "TextBody")
            TextBodySmall(text = "TextBodySmall")
        }
    }
}
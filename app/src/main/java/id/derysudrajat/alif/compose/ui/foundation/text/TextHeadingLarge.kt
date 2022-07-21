package id.derysudrajat.alif.compose.ui.foundation.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.compose.ui.theme.AlifThemes
import id.derysudrajat.alif.compose.ui.theme.Black
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.White

@Composable
fun TextHeadingLarge(
    modifier: Modifier? = Modifier,
    text: String,
    textColor: Color? = null,
    textStyle: TextStyle? = null,
    textAlign: TextAlign? = null,
) {
    Text(
        modifier = modifier ?: Modifier,
        text = text,
        color = textColor ?: if (isSystemInDarkTheme()) White else Black,
        textAlign = textAlign,
        style = textStyle ?: AlifThemes.Typography.headingLarge
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextHeadingLarge() {
    MaterialTheme {
        TextHeadingLarge(text = "Add new schedule", textColor = Primary)
    }
}
package id.derysudrajat.alif.compose.ui.foundation.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.theme.Black
import id.derysudrajat.alif.compose.ui.theme.White

@Composable
fun TextArabic(
    modifier: Modifier? = Modifier,
    text: String,
    textColor: Color? = null,
    textStyle: TextStyle? = null,
    textAlign: TextAlign? = null,
    fontSize: TextUnit? = null
) {
    val fontFamily = FontFamily(Font(R.font.alif_quran, FontWeight.Normal))
    Text(
        modifier = modifier ?: Modifier,
        text = text,
        color = textColor ?: if (isSystemInDarkTheme()) White else Black,
        textAlign = textAlign,
        style = textStyle ?: TextStyle(
            fontSize = fontSize ?: 24.sp,
            lineHeight = 48.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Normal,
        )
    )
}

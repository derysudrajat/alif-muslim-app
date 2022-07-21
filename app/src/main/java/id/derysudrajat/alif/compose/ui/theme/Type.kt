package id.derysudrajat.alif.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.derysudrajat.alif.R

// Set of Material typography styles to start with

val alifFontFamily = FontFamily(
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_medium, weight = FontWeight.SemiBold),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
)

val AlifTypography = Typography(
    h1 = TextStyle(
        fontFamily = alifFontFamily,
        fontSize = 26.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontFamily = alifFontFamily,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.Bold
    ),
    h3 = TextStyle(
        fontSize = 18.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Bold
    ),
    h4 = TextStyle( // title
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    h5 = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    subtitle1 = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    body1 = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal
    ),
    body2 = TextStyle(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Light
    ),

    )
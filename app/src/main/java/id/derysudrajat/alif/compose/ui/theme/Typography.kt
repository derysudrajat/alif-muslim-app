package id.derysudrajat.alif.compose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import id.derysudrajat.alif.R

val DefaultFontFamily = FontFamily(
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_medium, weight = FontWeight.SemiBold),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
)

@Immutable
class Typography internal constructor(
    val headingXLarge: TextStyle,
    val headingLarge: TextStyle,
    val heading: TextStyle,
    val title: TextStyle,
    val titleSmall: TextStyle,
    val subtitle: TextStyle,
    val body: TextStyle,
    val bodySmall: TextStyle
) {
    constructor(
        defaultFontFamily: FontFamily = DefaultFontFamily,
        headingXLarge: TextStyle = TextStyle(
            fontSize = 26.sp,
            lineHeight = 38.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        headingLarge: TextStyle = TextStyle(
            fontSize = 20.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        heading: TextStyle = TextStyle(
            fontSize = 18.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.ExtraBold
        ),
        title: TextStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Bold
        ),
        titleSmall: TextStyle = TextStyle(
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Bold
        ),
        subtitle: TextStyle = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold
        ),
        body: TextStyle = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        ),
        bodySmall: TextStyle = TextStyle(
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Normal
        )
    ) : this(
        headingXLarge = headingXLarge.withDefaultFontFamily(default = defaultFontFamily),
        headingLarge = headingLarge.withDefaultFontFamily(default = defaultFontFamily),
        heading = heading.withDefaultFontFamily(default = defaultFontFamily),
        title = title.withDefaultFontFamily(default = defaultFontFamily),
        titleSmall = titleSmall.withDefaultFontFamily(default = defaultFontFamily),
        subtitle = subtitle.withDefaultFontFamily(default = defaultFontFamily),
        body = body.withDefaultFontFamily(default = defaultFontFamily),
        bodySmall = bodySmall.withDefaultFontFamily(default = defaultFontFamily)
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Typography

        if (headingXLarge != other.headingXLarge) return false
        if (headingLarge != other.headingLarge) return false
        if (heading != other.heading) return false
        if (title != other.title) return false
        if (titleSmall != other.titleSmall) return false
        if (subtitle != other.subtitle) return false
        if (body != other.body) return false
        if (bodySmall != other.bodySmall) return false

        return true
    }

    override fun hashCode(): Int {
        var result = headingXLarge.hashCode()
        result = 31 * result + headingLarge.hashCode()
        result = 31 * result + heading.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + titleSmall.hashCode()
        result = 31 * result + subtitle.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + bodySmall.hashCode()
        return result
    }


}

private fun TextStyle.withDefaultFontFamily(default: FontFamily): TextStyle {
    return if (fontFamily != null) this else copy(fontFamily = default)
}

internal val LocalTypography = staticCompositionLocalOf { Typography() }
package id.derysudrajat.alif.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle

private val DarkColorPalette = darkColors(
    primary = Primary,
    primaryVariant = Black,

    secondary = Primary,
    secondaryVariant = Primary,
)

private val LightColorPalette = lightColors(
    primary = Primary,
    primaryVariant = White,

    secondary = Primary,
    secondaryVariant = Primary,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

object AlifThemes {

    private val typography: androidx.compose.material.Typography
        @Composable
        @ReadOnlyComposable
        get() = AlifTypography

    val Colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

    object Typography {
        val headingXLarge: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.h1

        val headingLarge: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.h2

        val heading: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.h3

        val title: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.h4

        val titleSmall: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.h5

        val subtitle: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.subtitle1

        val body: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.body1

        val bodySmall: TextStyle
            @Composable
            @ReadOnlyComposable
            get() = typography.body2
    }
}


@Composable
fun AlifTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
        colors = colors,
        typography = AlifTypography,
        shapes = Shapes,
        content = content
    )
}
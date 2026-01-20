package id.derysudrajat.alif.ui.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
private fun setColor(
    lightColor: Color,
    darkColor: Color
): Color {
    return if (isSystemInDarkTheme()) darkColor else lightColor
}

object AppColor {
    object Primary {
        val Main = Color(0xFF007063)
        val Light = Color(0xFF459f90)
        val Dark = Color(0xFF004439)
        val Transparent = Color(0x1A007063)
    }

    object Secondary {
        val Main = Color(0xFFffbf6b)
        val Light = Color(0xFFfff29b)
        val Dark = Color(0xFFc98f3d)
    }

    object Black {
        val Main = Color(0xFF000000)
        val Main_10 = Color(0x0D000000)
        val Main_60 = Color(0xFFC6C6C6)
    }

    object White {
        val Main = Color(0xFFFFFFFF)
        val Main_20 = Color(0x33FFFFFF)
        val Main_10 = Color(0x1AFFFFFF)
    }

    val Gray = Color(0xFFE5E5E5)

    val Background @Composable get() = setColor(
        lightColor = White.Main,
        darkColor = Black.Main
    )
    val Text @Composable get() = setColor(
        lightColor = Black.Main,
        darkColor = White.Main
    )
}
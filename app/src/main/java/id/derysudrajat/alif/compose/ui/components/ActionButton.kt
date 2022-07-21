package id.derysudrajat.alif.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.theme.*

@Composable
fun ActionButton(
    modifier: Modifier? = Modifier, icon: Int, type: Int? = 0, action: () -> Unit
) {
    Button(
        modifier = (modifier ?: Modifier)
            .width(56.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp)),
        onClick = { action() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSystemInDarkTheme() || type == 1) White10 else Black10
        ),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                if (isSystemInDarkTheme() || type == 1) White else Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActionButtonDark() {
    AlifTheme {
        ActionButton(icon = R.drawable.ic_repeat, type = 1) {}
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewActionButton() {
    AlifTheme {
        ActionButton(icon = R.drawable.ic_repeat) {}
    }
}
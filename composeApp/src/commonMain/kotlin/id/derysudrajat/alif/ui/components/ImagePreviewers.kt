package id.derysudrajat.alif.ui.components

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.bg_schedule
import alifmuslimapp.composeapp.generated.resources.ic_compas
import alifmuslimapp.composeapp.generated.resources.ic_quran
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource


@Preview
@Composable
private fun ImagePreviewers() {
    Column {
        Image(
            painter = painterResource(Res.drawable.bg_schedule),
            contentDescription = null
        )
        Image(
            painter = painterResource(Res.drawable.ic_quran),
            contentDescription = null
        )
        Image(
            painter = painterResource(Res.drawable.ic_compas),
            contentDescription = null
        )
    }
}


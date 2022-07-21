package id.derysudrajat.alif.compose.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.Black
import id.derysudrajat.alif.compose.ui.theme.White

@Composable
fun BaseTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) Black else White)
            .padding(16.dp),
        verticalAlignment = CenterVertically
    ) {
        if (onBack != null) {
            ButtonBack(onBack)
            Spacer(modifier = Modifier.width(16.dp))
        } else {
            Spacer(modifier = Modifier.height(56.dp))
        }
        TextTitle(
            modifier = Modifier
                .align(CenterVertically), text = title
        )
    }

}

@Composable
private fun ButtonBack(onBack: () -> Unit) {
    ActionButton(icon = R.drawable.ic_arrow_back, action = onBack)
}

@Preview(showBackground = true)
@Composable
private fun PreviewButtonBack() {
    AlifTheme {
        ButtonBack {}
    }
}

@Preview
@Composable
private fun PreviewBaseTopBar() {
    BaseTopBar("Some Title") {}
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun PreviewBaseTopBarDark() {
    BaseTopBar("Some Title in Dark") {}
}
package id.derysudrajat.alif.ui.components

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_back
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.ui.themes.AppColor
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String = "",
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(text = title, color = AppColor.Text, fontWeight = FontWeight.Bold)
        },
        navigationIcon = if (onBack != null) {
            {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        tint = AppColor.Text
                    )
                }
            }
        } else {
            {}
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppColor.Background
        )
    )
}

@Preview
@Composable
private fun PreviewAppTopBar() {
    AppTopBar() {

    }
}
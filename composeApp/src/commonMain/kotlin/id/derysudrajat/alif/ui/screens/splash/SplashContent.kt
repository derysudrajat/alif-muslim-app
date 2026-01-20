package id.derysudrajat.alif.ui.screens.splash

import alifmuslimapp.composeapp.generated.resources.Res
import alifmuslimapp.composeapp.generated.resources.ic_alif
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.ui.themes.AppColor
import org.jetbrains.compose.resources.painterResource


@Composable
fun SplashContent() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColor.Primary.Main
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(Res.drawable.ic_alif),
                contentDescription = ""
            )
        }
    }
}


@Preview
@Composable
private fun PreviewSplashContent() {
    MaterialTheme {
        SplashContent()
    }
}
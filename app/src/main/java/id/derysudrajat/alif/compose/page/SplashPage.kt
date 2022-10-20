package id.derysudrajat.alif.compose.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.AlifThemes

@Composable
fun SplashPage() {
    Scaffold(
        backgroundColor = AlifThemes.Colors.primary
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Image(modifier = Modifier.constrainAs(createRef()) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
            }, painter = painterResource(id = R.drawable.ic_alif), contentDescription = "App Icon")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSplashPage() {
    AlifTheme {
        SplashPage()
    }
}
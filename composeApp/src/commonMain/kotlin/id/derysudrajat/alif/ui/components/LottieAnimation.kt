package id.derysudrajat.alif.ui.components

import alifmuslimapp.composeapp.generated.resources.Res
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter


@Composable
fun LottieAnimation(
    resourcePath: String,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(resourcePath).decodeToString()
        )
    }
    Image(
        modifier = modifier,
        painter = rememberLottiePainter(
            composition = composition,
            iterations = Compottie.IterateForever
        ),
        contentDescription = "Lottie animation"
    )
}

@Preview
@Composable
private fun PreviewLottieAnimation() {
    LottieAnimation("files/anim_day.json")
}
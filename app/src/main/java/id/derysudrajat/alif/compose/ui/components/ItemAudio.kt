package id.derysudrajat.alif.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.theme.Gray
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.Secondary
import id.derysudrajat.alif.compose.ui.theme.White

@Composable
fun ItemAudio(
    audioProgress: Float,
    isFinish: Boolean? = false,
    position: Int,
    onStart: (currentAudioProgress: Float, position: Int) -> Unit, onPause: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),

        ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val (btnAudio, progress) = createRefs()
            var isPlaying by remember { mutableStateOf(false) }
            if (isFinish != null) isPlaying = false
            Box(modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    isPlaying = !isPlaying
                    if (isPlaying) onStart(audioProgress, position) else onPause()
                }
                .background(Primary)
                .constrainAs(btnAudio) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }, contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(id = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(White)
                )
            }

            LinearProgressIndicator(
                progress = audioProgress,
                modifier = Modifier
                    .height(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .constrainAs(progress) {
                        top.linkTo(parent.top)
                        start.linkTo(btnAudio.end, margin = 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                backgroundColor = Gray,
                color = Secondary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewItemAudio() {
    ItemAudio(0f, null, 0, { a, b -> }, {})
}
package id.derysudrajat.alif.compose.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.components.ActionButton
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeading
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeadingXLarge
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.data.model.RotationTarget

@Composable
fun CompassPage(
    isFacingQilba: Boolean,
    qilbaRotation: RotationTarget,
    compassRotation: RotationTarget,
    locationAddress: String,
    goToBack: () -> Unit,
    refreshLocation: () -> Unit
) {
    Scaffold {
        ConstraintLayout(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            val (bg, back, refresh, title, degree, description, location, windDir, compass) = createRefs()
            val realDegree = 360 - qilbaRotation.to

            Image(
                painterResource(id = R.drawable.ic_bg_schedule),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.constrainAs(bg) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.ratio("1.4:1")
                })

            ActionButton(modifier = Modifier.constrainAs(back) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(parent.start, margin = 32.dp)
            }, icon = R.drawable.ic_arrow_back, type = 1, goToBack)

            ActionButton(modifier = Modifier.constrainAs(refresh) {
                top.linkTo(parent.top, margin = 32.dp)
                end.linkTo(parent.end, margin = 32.dp)
            }, icon = R.drawable.ic_repeat, type = 1, refreshLocation)

            TextTitle(modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(back.top)
                bottom.linkTo(back.bottom)
            }, text = "Qibla", textColor = White)

            TextHeadingXLarge(
                modifier = Modifier.constrainAs(degree) {
                    bottom.linkTo(description.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, text = "${realDegree.toInt()}°", textColor = White
            )

            Row(modifier = Modifier.constrainAs(description) {
                bottom.linkTo(location.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }) {
                if (realDegree in 1f..180f && !isFacingQilba) Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(White)
                )
                if (isFacingQilba) TextHeading(
                    text = "You're Facing the Qibla", textColor = White
                )
                if (realDegree in 181f..360f && !isFacingQilba) Image(
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(180f),
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(White)
                )
            }

            Row(
                modifier = Modifier.constrainAs(location) {
                    bottom.linkTo(bg.bottom, 32.dp)
                    start.linkTo(back.end)
                    end.linkTo(refresh.start)
                    width = Dimension.fillToConstraints
                }, horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "",
                    modifier = Modifier.size(22.dp)
                )
                TextBody(
                    modifier = Modifier.padding(top = 2.dp, start = 4.dp),
                    text = locationAddress,
                    textColor = White
                )
            }


            Image(
                modifier = Modifier
                    .rotate(compassRotation.to)
                    .constrainAs(windDir) {
                        top.linkTo(bg.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(
                    if (isSystemInDarkTheme()) R.drawable.ic_wind_direction_night
                    else R.drawable.ic_wind_direction
                ),
                contentDescription = "",
            )

            Image(
                modifier = Modifier
                    .rotate(qilbaRotation.to)
                    .constrainAs(compass) {
                        top.linkTo(windDir.top)
                        bottom.linkTo(windDir.bottom)
                        start.linkTo(windDir.start)
                        end.linkTo(windDir.end)
                    },
                painter = painterResource(id = R.drawable.ic_compass_direction),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCompassPage() {
    CompassPage(false,
        RotationTarget(0f, 294f),
        RotationTarget(0f, 0f),
        "Desa Muara, Kecamatan Suranenggala, Kabupaten Cirebon",
        {},
        {})
}
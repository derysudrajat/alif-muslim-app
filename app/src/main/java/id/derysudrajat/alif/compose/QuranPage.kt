package id.derysudrajat.alif.compose

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.compose.ui.components.BaseTopBar
import id.derysudrajat.alif.compose.ui.components.ItemSurah
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeading
import id.derysudrajat.alif.compose.ui.foundation.text.TextSubtitle
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.AlifThemes
import id.derysudrajat.alif.compose.ui.theme.Black10
import id.derysudrajat.alif.compose.ui.theme.White10
import id.derysudrajat.alif.data.model.Juz
import id.derysudrajat.alif.data.model.Surah

@Composable
fun QuranPage(
    listSurah: List<Surah>, listJuz: List<Juz>, onBack: () -> Unit
) {
    Scaffold(topBar = {
        BaseTopBar(title = "Al-Qur'an", onBack)
    }) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (tabLayout, content) = createRefs()
            var tabIndex by remember { mutableStateOf(0) }
            val tabTitles = listOf("Surah", "Juz", "Bookmark")
            Box(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(tabLayout) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }) {
                TabRow(selectedTabIndex = tabIndex,
                    contentColor = AlifThemes.Colors.primary,
                    backgroundColor = if (isSystemInDarkTheme()) White10 else Black10,
                    indicator = { position ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffsetCustom(position[tabIndex], 48.dp)
                                .height(4.dp)
                                .background(
                                    color = AlifThemes.Colors.primary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                        )
                    }) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(selected = tabIndex == index,
                            onClick = { tabIndex = index },
                            text = { TextSubtitle(text = title) })
                    }
                }
            }
            val contentModifier = Modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(tabLayout.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            when (tabIndex) {
                0 -> ListSurah(contentModifier, listSurah)
                1 -> ListJuz(contentModifier, listSurah, listJuz)
                2 -> ListBookmark(contentModifier)
            }
        }
    }
}

@Composable
fun ListSurah(modifier: Modifier, listSurah: List<Surah>) {
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = listSurah.size,
            key = {
                listSurah[it].index
            },
            itemContent = {
                ItemSurah(surah = listSurah[it])
            }

        )
    }
}

@Composable
fun ListJuz(modifier: Modifier, listSurah: List<Surah>, listJuz: List<Juz>) {
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(16.dp)
    ) {
        listJuz.forEach {
            val list = (it.start.index..it.end.index).toList()
            val includedSurah = listSurah.filter { surah -> list.contains(surah.index) }
            item {
                TextHeading(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp, end = 16.dp, top = 8.dp
                        ), text = "Juz ${it.index}"
                )
            }
            items(includedSurah) { surah ->
                ItemSurah(surah = surah)
            }
        }
    }
}

@Composable
fun ListBookmark(modifier: Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (noBookmark) = createRefs()
        TextTitle(modifier = Modifier.constrainAs(noBookmark) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, text = "There's no bookmark yet, let's add it")
    }
}

private fun Modifier.tabIndicatorOffsetCustom(
    currentTabPosition: TabPosition,
    customWidth: Dp,
): Modifier = composed(inspectorInfo = debugInspectorInfo {
    name = "tabIndicatorOffset"
    value = currentTabPosition
}) {
    val currentTabWidth by animateDpAsState(
        targetValue = customWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset + ((currentTabPosition.width - currentTabWidth) / 2))
        .width(currentTabWidth)
}

@Preview
@Composable
private fun PreviewQuranPage() {
    AlifTheme {
        QuranPage(listOf(), listOf()) {}
    }
}
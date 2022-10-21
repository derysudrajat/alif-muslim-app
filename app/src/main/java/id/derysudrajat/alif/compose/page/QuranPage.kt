package id.derysudrajat.alif.compose.page

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import id.derysudrajat.alif.compose.state.QuranUiState
import id.derysudrajat.alif.compose.ui.components.BaseTopBar
import id.derysudrajat.alif.compose.ui.components.ItemSurah
import id.derysudrajat.alif.compose.ui.foundation.text.TextHeading
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.navigation.QuranBottomNav
import id.derysudrajat.alif.compose.ui.navigation.QuranNavDrawer
import id.derysudrajat.alif.compose.ui.navigation.QuranNavType
import id.derysudrajat.alif.compose.ui.navigation.QuranNavigationRail
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.data.model.Juz
import id.derysudrajat.alif.data.model.Surah

@Composable
fun QuranPage(
    onBack: () -> Unit,
    content: @Composable RowScope.(Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            BaseTopBar(title = "Al-Qur'an", onBack)
        }
    ) {
        val modifier = Modifier
            .padding(it)
            .fillMaxSize()
        Row(modifier) {
            content(modifier)
        }
    }
}

@Composable
fun QuranContent(
    modifier: Modifier,
    quranUiState: QuranUiState,
    navType: QuranNavType,
    onClick: (Surah) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (tabLayout, content, navRail, navDraw) = createRefs()
        var tabIndex by remember { mutableStateOf(0) }

        when (navType) {
            QuranNavType.BottomNav -> {
                QuranBottomNav(modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .constrainAs(tabLayout) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    }, tabIndex = tabIndex, onClick = { tabIndex = it })
            }

            QuranNavType.NavRail -> {
                QuranNavigationRail(
                    Modifier.constrainAs(navRail) { start.linkTo(parent.start) }, tabIndex
                ) { tabIndex = it }
            }

            QuranNavType.NavDrawer -> {
                QuranNavDrawer(
                    Modifier.constrainAs(navDraw) { start.linkTo(parent.start) },
                    tabIndex, { tabIndex = it }) {
                    ContainerPage(Modifier.fillMaxSize(), tabIndex, quranUiState, onClick)
                }
            }
        }

        if (navType != QuranNavType.NavDrawer) {
            val contentModifier = Modifier.constrainAs(content) {
                top.linkTo(parent.top)
                start.linkTo(
                    if (navType == QuranNavType.NavRail) navRail.end else parent.start
                )
                end.linkTo(parent.end)
                bottom.linkTo(
                    if (navType == QuranNavType.BottomNav) tabLayout.top else parent.bottom
                )
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            ContainerPage(contentModifier, tabIndex, quranUiState, onClick)
        }
    }
}

@Composable
private fun ContainerPage(
    modifier: Modifier,
    tabIndex: Int,
    quranUiState: QuranUiState,
    onClick: (Surah) -> Unit
) {
    when (tabIndex) {
        0 -> ListSurah(modifier, quranUiState.listSurah, onClick)
        1 -> ListJuz(modifier, quranUiState.listSurah, quranUiState.listJuz, onClick)
        2 -> ListBookmark(modifier)
    }
}

@Composable
fun ListSurah(modifier: Modifier, listSurah: List<Surah>, onClick: (Surah) -> Unit) {
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = listSurah.size,
            key = {
                listSurah[it].index
            },
            itemContent = {
                ItemSurah(surah = listSurah[it], onClick)
            }
        )
    }
}

@Composable
fun ListJuz(
    modifier: Modifier,
    listSurah: List<Surah>,
    listJuz: List<Juz>,
    onClick: (Surah) -> Unit
) {
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
                ItemSurah(surah = surah, onClick)
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


@Preview(showBackground = true, widthDp = 1000)
@Composable
private fun PreviewQuranPage() {
    AlifTheme {
        QuranPage(onBack = { }) {}
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
private fun PreviewQuranContent() {
    AlifTheme {
        QuranContent(
            modifier = Modifier.fillMaxSize(),
            quranUiState = QuranUiState(),
            navType = QuranNavType.NavRail,
            onClick = {})
    }
}
package id.derysudrajat.alif.compose.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextSubtitle
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.AlifThemes
import id.derysudrajat.alif.compose.ui.theme.Black
import id.derysudrajat.alif.compose.ui.theme.Black10
import id.derysudrajat.alif.compose.ui.theme.Black60
import id.derysudrajat.alif.compose.ui.theme.Gray
import id.derysudrajat.alif.compose.ui.theme.Primary20
import id.derysudrajat.alif.compose.ui.theme.White
import id.derysudrajat.alif.compose.ui.theme.White10

enum class QuranNavType {
    BottomNav, NavRail, NavDrawer
}

@Composable
fun QuranBottomNav(modifier: Modifier, tabIndex: Int, onClick: (index: Int) -> Unit) {
    val tabTitles = listOf("Surah", "Juz", "Bookmark")
    Box(modifier = modifier) {
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
                    onClick = { onClick(index) },
                    text = { TextSubtitle(text = title) })
            }
        }
    }
}

@Composable
fun QuranNavigationRail(modifier: Modifier, currentIndex: Int, onClick: (index: Int) -> Unit) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        NavigationRailItem(
            selected = currentIndex == 0,
            onClick = { onClick(0) },
            label = { TextBody(text = "Surah") },
            unselectedContentColor = Gray,
            icon = { Icon(imageVector = Icons.Rounded.Description, contentDescription = "") }
        )
        NavigationRailItem(
            selected = currentIndex == 1,
            onClick = {
                onClick(1)
            },
            label = { TextBody(text = "Juz") },
            unselectedContentColor = Black60,
            icon = { Icon(imageVector = Icons.Rounded.Book, contentDescription = "") }
        )
        NavigationRailItem(
            selected = currentIndex == 2,
            onClick = { onClick(2) },
            label = { TextBody(text = "Bookmark") },
            unselectedContentColor = Black60,
            icon = { Icon(imageVector = Icons.Rounded.Bookmark, contentDescription = "") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranNavDrawer(
    modifier: Modifier,
    currentIndex: Int,
    onClick: (index: Int) -> Unit,
    content: @Composable () -> Unit
) {
    PermanentNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            Column(
                Modifier
                    .width(240.dp)
                    .fillMaxHeight()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NavigationDrawerItem(
                    selected = currentIndex == 0,
                    label = {
                        TextBody(
                            text = "Surah",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Description,
                            contentDescription = "",
                            tint = if (isSystemInDarkTheme()) White else Black
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Black10,
                        selectedContainerColor = Primary20,
                    ),
                    onClick = { onClick(0) }
                )

                NavigationDrawerItem(
                    selected = currentIndex == 1,
                    label = {
                        TextBody(
                            text = "Juz",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Book,
                            contentDescription = "",
                            tint = if (isSystemInDarkTheme()) White else Black
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Black10,
                        selectedContainerColor = Primary20,
                    ),
                    onClick = { onClick(1) }
                )

                NavigationDrawerItem(
                    selected = currentIndex == 2,
                    label = {
                        TextBody(
                            text = "Bookmarks",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Bookmark,
                            contentDescription = "",
                            tint = if (isSystemInDarkTheme()) White else Black
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Black10,
                        selectedContainerColor = Primary20,
                    ),
                    onClick = { onClick(2) }
                )

            }
        },
        content = { content() }
    )
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

@Preview(showBackground = true)
@Composable
private fun PreviewBottomNavigation() {
    AlifTheme {
        QuranBottomNav(modifier = Modifier, tabIndex = 0, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNavigationRail() {
    AlifTheme {
        QuranNavigationRail(Modifier, 0) {

        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
private fun PreviewNavigationDrawer() {
    AlifTheme {
        QuranNavDrawer(Modifier, 0, {}, {})
    }
}
package id.derysudrajat.alif.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeContent(
    goToCalendar: () -> Unit
) {
    val viewmodel = koinViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        viewmodel.requestLocation()
    }
    Scaffold(
        containerColor = AppColor.Background,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            stickyHeader {
                HeaderHome()
            }
            item {
                ScheduleTimeCard()
            }
            item {
                HomeTaskItem()
            }
            item {
                HomeCalendarItem(
                    onClick = goToCalendar
                )
            }
            items(6) {
                ScheduleItem()
            }
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewHomeContent() {
    MaterialTheme {
        HomeContent(
            goToCalendar = {}
        )
    }
}
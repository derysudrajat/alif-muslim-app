package id.derysudrajat.alif.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.permissions.compose.BindEffect
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeContent(
    goToCalendar: () -> Unit
) {
    val viewmodel = koinViewModel<MainViewModel>()
    val (result, setResult) = remember { mutableStateOf("No-Result") }

    BindEffect(viewmodel.getPermissionsController())

    LaunchedEffect(Unit) {
        viewmodel.requestLocation(
            onSuccess = {
                setResult("Location: $it")
            },
            onFailed = {
                setResult("Failed: $it")
            }
        )
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
                Text(result, color = AppColor.Text)
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
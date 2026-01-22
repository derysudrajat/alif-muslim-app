package id.derysudrajat.alif.ui.screens.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.ui.components.AppTopBar
import id.derysudrajat.alif.ui.themes.AppColor
import id.derysudrajat.alif.utils.PreviewLightDarkWithBackground


@Composable
fun IslamicCalendarContent(
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColor.Background,
        topBar = {
            AppTopBar(
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            IslamicCalendarComponent(Modifier.padding(16.dp))
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(10) {
                    IslamicEventItem()
                }
            }
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
private fun PreviewIslamicCalendarContent() {
    MaterialTheme {
        IslamicCalendarContent(
            onBack = {}
        )
    }
}
package id.derysudrajat.alif.compose.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.derysudrajat.alif.compose.ui.foundation.text.TextBody
import id.derysudrajat.alif.compose.ui.foundation.text.TextTitle
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.compose.ui.theme.Primary
import id.derysudrajat.alif.compose.ui.theme.White

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun SampleNested() {
    Scaffold(Modifier.padding(16.dp)) { padding ->
        LazyColumn(
            contentPadding = padding
        ) {
            item { TextTitle(text = "Horizontal List") }
            item {
                LazyRow(
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(10) {
                        ItemRow(title = "$it")
                    }
                }
            }
            item { TextTitle(text = "Vertical List") }
            items(15) {
                TextBody(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Primary)
                        .padding(16.dp), text = "This is List - $it", textColor = White
                )
            }
        }
    }
}

@Composable
fun ItemRow(title: String) {
    Box(
        Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Primary)
            .padding(16.dp)
    ) {
        TextBody(text = "Item - $title", textColor = White)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemRow() {
    AlifTheme {
        ItemRow(title = "10")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun PreviewNested() {
    AlifTheme {
        SampleNested()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlifTheme {
        Greeting("Android")
    }
}
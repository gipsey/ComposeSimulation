package david.composesimulation.ui.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NestedScroll1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        )
        List()
    }
}

@Composable
private fun List() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            count = 100,
            key = { index -> index },
        ) { index ->
            Text(
                text = "$index",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(colors[index % colors.size])
            )
        }
    }
}

@Preview
@Composable
private fun NestedScroll1Preview() {
    NestedScroll1()
}

private val colors = listOf(
    Color.Gray,
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Yellow,
    Color.Cyan,
    Color.Magenta,
)

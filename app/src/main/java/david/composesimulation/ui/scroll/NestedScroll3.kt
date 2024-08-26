package david.composesimulation.ui.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val topHeight = 200.dp

@Composable
fun NestedScroll3() {
    Box(
        modifier = Modifier
    ) {
        Top(
            modifier = Modifier
                .height(height = topHeight),
        )
        List(
            modifier = Modifier
                .padding(top = topHeight),
        )
    }
}

@Composable
private fun Top(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    )
}

@Composable
private fun List(
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
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
private fun NestedScroll3Preview() {
    NestedScroll3()
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

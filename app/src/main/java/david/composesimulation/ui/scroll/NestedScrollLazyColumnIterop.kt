package david.composesimulation.ui.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NestedScrollLazyColumnIterop() {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        Top()
        LazyList()
    }
}

@Composable
private fun Top() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .layout { measurable, constraints ->
                println("dddd Top $measurable")
                println("dddd Top $constraints")

                val placeable = measurable.measure(constraints)

                layout(
                    width = placeable.width,
                    height = placeable.height,
                ) {
                    placeable.place(x = 0, y = 0)
                }
            }
    ) {
        Text(
            text = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10"
        )
    }
}

@Composable
private fun LazyList() {
    val state = rememberLazyListState()

    LazyColumn(
        state = state,
        userScrollEnabled = true,
        modifier = Modifier
            .layout { measurable, constraints ->
                println("dddd LazyList $measurable")
                println("dddd LazyList $constraints")

                val placeable = measurable.measure(constraints)

                layout(
                    width = placeable.width,
                    height = placeable.height,
                ) {
                    placeable.place(x = 0, y = 0)
                }
            }
    ) {
        items(
            count = 100,
            key = { index -> index },
        ) { index ->
            Content(index = index)
        }
    }
}

@Composable
private fun Content(index: Int) {
    Text(
        text = "$index",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(colors[index % colors.size])
    )
}

@Preview
@Composable
private fun NestedScrollLazyColumnIteropPreview() {
    NestedScrollLazyColumnIterop()
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

package david.composesimulation.ui.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp

@Composable
fun NestedScroll1() {
    val topContentVerticalOffset = remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                println("dddd onPreScroll ${available.y}")

                val topContentOffset = topContentVerticalOffset.floatValue
                val availableToConsume = available.y

                val toConsume =
                    if (0 < availableToConsume) {
                        // scrolling from top to bottom
                        if (-topContentOffset < availableToConsume) {
                            -topContentOffset
                        } else {
                            availableToConsume
                        }
                    } else {
                        // scrolling from bottom to top
                        val remainingOffset = 300f + topContentOffset

                        if (availableToConsume < -remainingOffset) {
                            -remainingOffset
                        } else {
                            availableToConsume
                        }
                    }

                topContentVerticalOffset.floatValue += toConsume

//                return Offset.Zero // not consuming anything
//                return Offset(x = 0f, y = +100f)
                return Offset(x = 0f, y = toConsume)
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                println("dddd onPostScroll consumed=${consumed.y} available=${available.y}")
//                return Offset.Zero
                return available
//                return consumed
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                println("dddd onPreFling ${available.y}")
                return Velocity.Zero
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                println("dddd onPostFling consumed=${consumed.y} available=${available.y}")
                return Velocity.Zero
            }
        }
    }
    Column(
        modifier = Modifier
            .nestedScroll(
                connection = nestedScrollConnection,
            )
    ) {
        Top { topContentVerticalOffset.floatValue }

//        List { topContentVerticalOffset.floatValue }
//        List { 0f }

        LazyList(userScrollEnabled = true) { topContentVerticalOffset.floatValue }
//        LazyList { 0f }

//        LazyList(userScrollEnabled = false) { topContentVerticalOffset.floatValue }
//        LazyList(userScrollEnabled = false) { 0f }
    }
}

@Composable
private fun Top(
    topContentVerticalOffsetProvider: () -> Float,
) {
    Box(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = topContentVerticalOffsetProvider().toInt()) }
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.LightGray)
    ) {
        Text(
            text = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10"
        )
    }
}

@Composable
private fun List(
    topContentVerticalOffsetProvider: () -> Float,
) {
    Column(
        modifier = Modifier
            .offset { IntOffset(x = 0, y = topContentVerticalOffsetProvider().toInt()) }
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        repeat(100) { index ->
            Content(index)
        }
    }
}

@Composable
private fun LazyList(
    userScrollEnabled: Boolean = true,
    topContentVerticalOffsetProvider: () -> Float,
) {
    LazyColumn(
        userScrollEnabled = userScrollEnabled,
        modifier = Modifier
            .offset { IntOffset(x = 0, y = topContentVerticalOffsetProvider().toInt()) }
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

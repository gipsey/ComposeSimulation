@file:OptIn(ExperimentalMaterial3Api::class)

package david.composesimulation.ui.scroll

import androidx.annotation.Px
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun NestedScroll4() {
    val state = rememberSaveable(saver = MyTopAppBarState.Saver) { MyTopAppBarState() }
    val scrollBehavior = myExitUntilCollapsedScrollBehavior(state = state)
//
//    println("ddddd scrollBehavior.state.heightOffsetLimit ${scrollBehavior.state.heightOffsetLimit}")
//    println("ddddd scrollBehavior.state.heightOffset ${scrollBehavior.state.heightOffset}")
//    println("ddddd scrollBehavior.state.contentOffset ${scrollBehavior.state.contentOffset}")
//    println("ddddd scrollBehavior.state.collapsedFraction ${scrollBehavior.state.collapsedFraction}")
//    println("ddddd scrollBehavior.state")

    ContentLayout(
        topContent = {
            println("dddddd topContent")
            Top(
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = scrollBehavior.state.heightOffset.roundToInt())
                    }
                    .draggable(
                        orientation = Orientation.Vertical,
                        state = rememberDraggableState { delta -> scrollBehavior.state.heightOffset += delta },
                        onDragStopped = { velocity ->
                            settleAppBar(
                                state = scrollBehavior.state,
                                velocity = velocity,
                                flingAnimationSpec = scrollBehavior.flingAnimationSpec!!,
                            )
                        }
                    )
            )
        },
        listContent = {
            println("dddddd listContent")
            List(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = 0,
                            y = scrollBehavior.state.topContentHeight + scrollBehavior.state.heightOffset.roundToInt()
                        )
                    }
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            )
        },
        onTopContentHeightMeasure = { height: TopContentHeightPx ->
            println("dddddd onTopContentHeightMeasure $height")
            scrollBehavior.state.topContentHeight = height
        },
        onContentMeasure = { measurement: ContentMeasurement ->
            println("dddddd onContentMeasure measurement $measurement")
            scrollBehavior.state.heightOffsetLimit =
                measurement.run {
                    if (entireContentHeight < layoutHeight) {
                        0f
                    } else {
                        -minOf(topContentHeight, entireContentHeight - layoutHeight).toFloat()
                    }
                }
        },
    )
}

@Composable
private fun ContentLayout(
    topContent: @Composable () -> Unit,
    listContent: @Composable () -> Unit,
    onTopContentHeightMeasure: (TopContentHeightPx) -> Unit,
    onContentMeasure: (ContentMeasurement) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val maxHeight = constraints.maxHeight
        val topPlaceable =
            subcompose(slotId = ContentLayoutSlots.TOP, content = topContent)
                .first()
                .measure(constraints = constraints)

        val topHeight = topPlaceable.height
        onTopContentHeightMeasure(topHeight)

        val listPlaceable =
            subcompose(slotId = ContentLayoutSlots.LIST, content = listContent)
                .first()
                .measure(constraints = constraints)
        val listHeight = listPlaceable.height
        val contentHeight = topHeight + listHeight

        onContentMeasure(
            ContentMeasurement(
                layoutHeight = maxHeight,
                topContentHeight = topHeight,
                entireContentHeight = contentHeight,
            )
        )

        layout(
            width = constraints.maxWidth, // for simplicity, deliberately using the max available width
            height = maxHeight, // for simplicity, deliberately using the max available height
        ) {
            topPlaceable.place(0, 0)
            listPlaceable.place(0, 0)
        }
    }
}

private enum class ContentLayoutSlots {
    TOP,
    LIST,
}

private typealias TopContentHeightPx = Int

private data class ContentMeasurement(
    @Px val layoutHeight: Int,
    @Px val topContentHeight: Int,
    @Px val entireContentHeight: Int,
)

@Composable
private fun Top(
    modifier: Modifier,
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Text(
            text = "0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19"
        )
    }
}

@Composable
private fun List(
    modifier: Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        val totalItemsCount = 4

        items(
            count = totalItemsCount,
            key = { index -> index },
        ) { index ->
            println("ddddd List - index $index out of ${totalItemsCount - 1}")

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

@Stable
class MyTopAppBarState {

    var topContentHeight by mutableIntStateOf(0)

    var heightOffsetLimit by mutableFloatStateOf(0f)

    private var _heightOffset = mutableFloatStateOf(0f)

    var heightOffset: Float
        get() = _heightOffset.floatValue
        set(newOffset) {
            _heightOffset.floatValue = newOffset.coerceIn(minimumValue = heightOffsetLimit, maximumValue = 0f)
        }

    var contentOffset by mutableFloatStateOf(0f)

    val collapsedFraction: Float
        get() = if (heightOffsetLimit != 0f) heightOffset / heightOffsetLimit else 0f

    companion object {

        val Saver: Saver<MyTopAppBarState, *> =
            listSaver(
                save = { listOf(it.heightOffsetLimit, it.heightOffset, it.contentOffset) },
                restore = {
                    MyTopAppBarState()
                        .apply {
                            heightOffsetLimit = it[0]
                            heightOffset = it[1]
                            contentOffset = it[2]
                        }
                }
            )
    }
}

@Preview
@Composable
private fun NestedScroll4Preview() {
    NestedScroll4()
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

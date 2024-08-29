@file:OptIn(ExperimentalMaterial3Api::class)

package david.composesimulation.ui.scroll

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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
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

/**

scrollState
1. when top is dragged/scrolled, then it is written
scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
2. heightOffset should be applied to top

nested scroll connection

top
has drag handler



list
has nestedScroll (nestedScrollConnection)

 */

private val topHeight = 300.dp

@Composable
fun NestedScroll4() {
    val topAppBarState = rememberTopAppBarState(
        initialHeightOffsetLimit = 0f, // todo
        initialHeightOffset = 0f,
        initialContentOffset = 0f,
    )
    val scrollBehavior = myExitUntilCollapsedScrollBehavior(
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = topAppBarState,
//        flingAnimationSpec = null, // a felso tartalom visszahozasa
    )
    val nestedScrollConnection = scrollBehavior.nestedScrollConnection

    println("ddddd scrollBehavior.state.heightOffsetLimit ${scrollBehavior.state.heightOffsetLimit}")
    println("ddddd scrollBehavior.state.heightOffset ${scrollBehavior.state.heightOffset}")
    println("ddddd scrollBehavior.state.contentOffset ${scrollBehavior.state.contentOffset}")
    println("ddddd scrollBehavior.state.collapsedFraction ${scrollBehavior.state.collapsedFraction}")
    println("ddddd scrollBehavior.state.overlappedFraction ${scrollBehavior.state.overlappedFraction}")
    println("ddddd scrollBehavior.state")

    ContentLayout(
        leadingContent = {
            println("dddddd leadingContent")
            Top(
                modifier = Modifier
                    .offset {
                        IntOffset(x = 0, y = scrollBehavior.state.heightOffset.roundToInt())
                    }
                    .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
                    },
                    onDragStopped = { velocity ->
                        settleAppBar(
                            state = scrollBehavior.state,
                            velocity = velocity,
                            flingAnimationSpec = scrollBehavior.flingAnimationSpec,
//                            snapAnimationSpec = null,
                        )
                    }
                )
            )
        },
        listContent = { leadingSlotHeightInPx: LeadingSlotHeightInPx ->
            println("dddddd listContent leadingSlotHeightInPx $leadingSlotHeightInPx")
            topAppBarState.heightOffsetLimit = -leadingSlotHeightInPx.toFloat() // todo set it differently

            List(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .offset {
                        IntOffset(x = 0, y = (leadingSlotHeightInPx + scrollBehavior.state.heightOffset).roundToInt())
                    }
            )
        }
    )
}

@Composable
fun ContentLayout(
    leadingContent: @Composable () -> Unit,
    listContent: @Composable (LeadingSlotHeightInPx) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val leadingPlaceable = subcompose(Slots.Leading, leadingContent).first().measure(constraints)
        val listPlaceable = subcompose(Slots.List) { listContent(leadingPlaceable.height) }.first().measure(constraints)
        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            leadingPlaceable.place(0, 0)
            listPlaceable.place(0, 0)
        }
    }
}

enum class Slots { Leading, List }

private typealias LeadingSlotHeightInPx = Int

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
        val totalItemsCount = 100

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

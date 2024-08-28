@file:OptIn(ExperimentalMaterial3Api::class)

package david.composesimulation.ui.scroll

import androidx.annotation.Px
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.abs
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
fun NestedScroll3() {
    Box(
        modifier = Modifier
    ) {
        val density = LocalDensity.current
        @Px val topHeightPx = remember(topHeight) { with(density) { topHeight.toPx() } }
        println("ddddd topHeightPx ${topHeightPx}")

        val topAppBarState = rememberTopAppBarState(
            initialHeightOffsetLimit = -topHeightPx,
            initialHeightOffset = 0f,
            initialContentOffset = 0f,
        )
        val scrollBehavior = MyExitUntilCollapsedScrollBehavior(
//        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            state = topAppBarState,
            snapAnimationSpec = null,
            flingAnimationSpec = null,
        )
        val nestedScrollConnection = scrollBehavior.nestedScrollConnection

        LaunchedEffect(key1 = Unit) {
            println("ddddd scrollBehavior.state.heightOffset ${scrollBehavior.state.heightOffset}")
        }

        Top(
            modifier = Modifier
                .height(height = topHeight)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
                    },
                    onDragStopped = { velocity ->
                        // todo
//                        nestedScrollConnection.onPostFling(consumed = velocity, available = velocity)
//                        settleAppBar(
//                            scrollBehavior.state,
//                            velocity,
//                            scrollBehavior.flingAnimationSpec,
//                            scrollBehavior.snapAnimationSpec
//                        )
                    }
                )
                .offset {
                    println("ddddd scrollBehavior.state.heightOffset ${scrollBehavior.state.heightOffset}")
                    IntOffset(x = 0, y = scrollBehavior.state.heightOffset.roundToInt())
                }
        )
        List(
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
                .offset {
                    println("ddddd scrollBehavior.state.heightOffset ${scrollBehavior.state.heightOffset}")
                    IntOffset(x = 0, y = (topHeightPx + scrollBehavior.state.heightOffset).roundToInt())
                }
        )
    }
}

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
            text = "1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12"
        )
    }
}

@Composable
private fun List(
    contentPadding: PaddingValues = PaddingValues(),
    modifier: Modifier,
) {
    LazyColumn(
        contentPadding = contentPadding,
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

@OptIn(ExperimentalMaterial3Api::class)
private class MyExitUntilCollapsedScrollBehavior(
    override val state: TopAppBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
) : TopAppBarScrollBehavior {

    override val isPinned: Boolean = false

    override var nestedScrollConnection =
        object : NestedScrollConnection {

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val availableY = available.y
                println("ddddd onPreScroll 1 - availableY $availableY")
                // Don't intercept if scrolling down.
                if (availableY > 0f) return Offset.Zero

                val prevHeightOffset = state.heightOffset
                println("ddddd onPreScroll 2 - prevHeightOffset $prevHeightOffset")
                state.heightOffset += availableY
                println("ddddd onPreScroll 3 - state.heightOffset = ${state.heightOffset}")


                return if (prevHeightOffset != state.heightOffset) {
                    // We're in the middle of top app bar collapse or expand.
                    println("ddddd onPreScroll 4 - return ${available.y}")
                    println("ddddd")
                    available
                } else {
                    println("ddddd onPreScroll 4 - return ${Offset.Zero.y}")
                    println("ddddd")
                    Offset.Zero
                }
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                val consumedY = consumed.y
                val availableY = available.y
                println("ddddd onPostScroll 1 - consumedY $consumedY")
                println("ddddd onPostScroll 2 - availableY $availableY")

                state.contentOffset += consumedY
                println("ddddd onPostScroll 3 - state.contentOffset ${state.contentOffset}")

                // scrolling up the content
                if (availableY < 0f || consumedY < 0f) {
                    println("ddddd onPostScroll 4 - scrolling up -  ${state.contentOffset}")
                    // When scrolling up, just update the state's height offset.
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += consumedY

                    println("ddddd")
                    println("ddddd")
                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }

                println("ddddd")
                println("ddddd")
                if (consumedY == 0f && availableY > 0) {
                    // Reset the total content offset to zero when scrolling all the way down. This
                    // will eliminate some float precision inaccuracies.
                    state.contentOffset = 0f
                }

                if (availableY > 0f) {
                    // Adjust the height offset in case the consumed delta Y is less than what was
                    // recorded as available delta Y in the pre-scroll.
                    val oldHeightOffset = state.heightOffset
                    state.heightOffset += availableY

                    return Offset(0f, state.heightOffset - oldHeightOffset)
                }

                return Offset.Zero
            }

//            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity =
//                settleAppBar(
//                    state = state,
//                    velocity = available.y,
//                    flingAnimationSpec = flingAnimationSpec,
//                    snapAnimationSpec = snapAnimationSpec
//                )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}

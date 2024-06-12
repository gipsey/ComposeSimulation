package david.composesimulation.ui.gesture

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

val draggableSize = 100.dp

enum class Anchor(val y: Dp) {
    Hidden(-draggableSize),
    Active(0.dp),
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnchoredDraggable() {
    val density = LocalDensity.current
    val anchors = DraggableAnchors {
        Anchor.entries.forEach { anchor ->
            anchor at with(density) { anchor.y.toPx() }
        }
    }
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = Anchor.Hidden,
            anchors = anchors,
            positionalThreshold = { totalDistance: Float ->
                println("positionalThreshold totalDistance $totalDistance")
                with(density) { draggableSize.toPx() }
            },
            velocityThreshold = {
                with(density) { 100.dp.toPx() }
            },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay(),
            confirmValueChange = { newValue: Anchor ->
                println("confirmValueChange newValue $newValue")
                true
            },
        )
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Vertical,
            )
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = 0,
                        y = anchoredDraggableState
                            .requireOffset()
                            .roundToInt()
                    )
                }
                .size(draggableSize)
                .background(Color.Green)
        )
    }
}

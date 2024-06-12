package david.composesimulation.ui.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun DraggableOfEitherHorizontalOrVertical() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val offsetX = remember { mutableFloatStateOf(0f) }
        val horizontalDraggableState = rememberDraggableState(
            onDelta = { delta: Float ->
                println("horizontalDraggableState onDelta delta $delta")
                offsetX.floatValue += delta
            }
        )

        val offsetY = remember { mutableFloatStateOf(0f) }
        val verticalDraggableState = rememberDraggableState(
            onDelta = { delta: Float ->
                println("verticalDraggableState onDelta delta $delta")
                offsetY.floatValue += delta
            }
        )

//        LaunchedEffect(verticalDraggableState) {
//            delay(1000)
//            verticalDraggableState.drag {
//                while (true) {
//                    delay(1000)
//                    dragBy(50f)
//                }
//            }
//        }

        Box(
            modifier = Modifier
                .offset { IntOffset(x = offsetX.floatValue.toInt(), y = offsetY.floatValue.toInt()) }
                .size(100.dp)
                .background(Color.Green)
                .draggable(
                    state = horizontalDraggableState,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { startedPosition: Offset ->
                        println("horizontal onDragStarted startedPosition $startedPosition")
                    },
                    onDragStopped = { velocity: Float ->
                        println("horizontal onDragStopped velocity $velocity")
                    },
                )
                .draggable(
                    state = verticalDraggableState,
                    orientation = Orientation.Vertical,
                    onDragStarted = { startedPosition: Offset ->
                        println("vertical onDragStarted startedPosition $startedPosition")
                    },
                    onDragStopped = { velocity: Float ->
                        println("vertical onDragStopped velocity $velocity")
                    },
                )
        )
    }
}

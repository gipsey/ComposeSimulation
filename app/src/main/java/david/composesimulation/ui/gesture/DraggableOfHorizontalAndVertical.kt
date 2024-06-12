package david.composesimulation.ui.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun DraggableOfHorizontalAndVertical() {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val offset = remember { mutableStateOf(Offset(0f, 0f)) }

        Box(
            modifier = Modifier
                .offset {
                    val value = offset.value
                    IntOffset(x = value.x.toInt(), y = value.y.toInt())
                }
                .size(100.dp)
                .background(Color.Green)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset: Offset ->
                            println("onDragStart offset $offset")
                        },
                        onDragEnd = {
                            println("onDragEnd")
                        },
                        onDragCancel = {
                            println("onDragCancel")
                        },
                        onDrag = { change: PointerInputChange, dragAmount: Offset ->
                            println("onDrag change $change dragAmount $dragAmount")
                            offset.value += dragAmount
                        }
                    )
                }
        )
    }
}

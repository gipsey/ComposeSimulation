package david.composesimulation.ui.subcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Element(
    val text: String,
    val widthInPixel: Int,
    val heightInPixel: Int,
    val color: Color,
)

private val elements =
    listOf(
        Element(text = "1", widthInPixel = 800, heightInPixel = 1200, color = Color.Green),
        Element(text = "2", widthInPixel = 1000, heightInPixel = 800, color = Color.Red),
        Element(text = "3", widthInPixel = 900, heightInPixel = 1200, color = Color.Gray),
        Element(text = "4", widthInPixel = 800, heightInPixel = 1300, color = Color.Blue),
        Element(text = "5", widthInPixel = 1100, heightInPixel = 900, color = Color.Cyan),
        Element(text = "6", widthInPixel = 1100, heightInPixel = 1200, color = Color.Magenta),
        Element(text = "7", widthInPixel = 300, heightInPixel = 300, color = Color.Yellow),
        Element(text = "8", widthInPixel = 6000, heightInPixel = 200, color = Color.DarkGray),
        Element(text = "9", widthInPixel = 500, heightInPixel = 300, color = Color.LightGray),
    )

@Composable
fun Subcompose3() {
    Column(
        modifier = Modifier
    ) {
        var lastDisplayedElementIndex by rememberSaveable { mutableStateOf<Int?>(null) }
        var elementIndex by rememberSaveable { mutableIntStateOf(0) }

        Button(
            enabled = (lastDisplayedElementIndex ?: 0) < elements.lastIndex,
            onClick = {
                lastDisplayedElementIndex?.let {
                    elementIndex = it + 1
                }
            },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Next page")
        }

        MultiElementPager(
            firsElementIndexToDisplay = elementIndex,
            elements = elements,
            elementContent = { element ->
                val density = LocalDensity.current

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp)
                        .size(
                            width = density.run { element.widthInPixel.toDp() },
                            height = density.run { element.heightInPixel.toDp() },
                        )
                        .background(color = element.color)
                ) {
                    Text(
                        text = element.text,
                        fontSize = 29.sp,
                    )
                }
            },
            onPageIsFull = { page ->
                lastDisplayedElementIndex = page
            }
        )
    }
}

@Composable
private fun MultiElementPager(
    firsElementIndexToDisplay: Int,
    elements: List<Element>,
    elementContent: @Composable (element: Element) -> Unit,
    onPageIsFull: (lastDisplayedElementIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints ->
        val maxHeight = constraints.maxHeight
        val placeables = mutableListOf<Placeable>()
        var isAvailableSpaceLeft = true
        var elementIndex = firsElementIndexToDisplay
        println("SegmentedLayout maxHeight $maxHeight elementIndex $elementIndex")

        while (isAvailableSpaceLeft) {
            val measurables = subcompose(slotId = elementIndex, content = { elementContent(elements[elementIndex]) })

            val currentPlaceables = measurables.map { measurable -> measurable.measure(constraints) }
            val currentPlaceablesAggregatedHeight = currentPlaceables.fold(0) { accumulator, element -> accumulator + element.height }

            val previousPlaceablesAggregatedHeight = placeables.fold(0) { accumulator, element -> accumulator + element.height }

            if (maxHeight - previousPlaceablesAggregatedHeight < currentPlaceablesAggregatedHeight) {
                isAvailableSpaceLeft = false

                onPageIsFull(elementIndex - 1)
            } else {
                placeables.addAll(currentPlaceables)
                elementIndex++

                if (elementIndex == elements.size) {
                    isAvailableSpaceLeft = false

                    onPageIsFull(elementIndex - 1)
                }
            }
        }

        val layoutWidth = constraints.maxWidth
        val layoutHeight = placeables.sumOf { placeable -> placeable.height }

        layout(
            width = layoutWidth,
            height = layoutHeight,
        ) {
            var y = 0

            placeables.forEach { placeable ->
                val horizontalSpace = (layoutWidth - placeable.width) / 2

                placeable.placeRelative(x = horizontalSpace, y = y)

                y += placeable.height
            }
        }
    }
}

@Preview
@Composable
private fun Subcompose3Preview() {
    Subcompose3()
}

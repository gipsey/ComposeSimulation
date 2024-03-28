package david.composesimulation.ui.subcompose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubCompose1() {
    val items = (0 until 1_000).toList()

    LazyLayout {
        items.forEach { value ->
            Text(
                text = value.toString(),
            )
        }
    }
}

@Composable
private fun LazyLayout(
    content: @Composable () -> Unit,
) {
    SubcomposeLayout(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) { constraints ->
        println("LazyLayout SubcomposeLayout constraints $constraints")

        val measurableList = subcompose(slotId = null, content = content)
        println("LazyLayout SubcomposeLayout measurableList $measurableList")

        val placeableList = measurableList.map { measurable ->
            measurable.measure(constraints)
        }

        val layoutWidth = placeableList.maxOf { placeable -> placeable.width }
        val layoutHeight = placeableList.sumOf { placeable -> placeable.height }

        layout(
            width = layoutWidth,
            height = layoutHeight,
            alignmentLines = emptyMap(),
        ) {
            var y = 0
            placeableList.forEach { placeable ->
                placeable.placeRelative(x = 0, y = y)
                y += placeable.height
            }
        }
    }
}

@Preview
@Composable
private fun SubCompose1Preview() {
    SubCompose1()
}

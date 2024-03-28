package david.composesimulation.ui.layout

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max

private data class TextPair(
    val main: String,
    val secondary: String,
)

@Composable
fun Layout1() {
    ContentWithLayout(
        list = listOf(
            TextPair(
                main = "abcd",
                secondary = "1234"
            ),
            TextPair(
                main = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz",
                secondary = "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
            ),
            TextPair(
                main = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz",
                secondary = "123456789"
            ),
            TextPair(
                main = "abcd",
                secondary = "12345678901234567890123456789012345678901234567890123456789012345678901234567890"
            ),
        )
    )
}

@Composable
private fun ContentWithLayout(list: List<TextPair>) {
    Column(
        verticalArrangement = spacedBy(48.dp),
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        list.forEach { textPair ->
            Layout(
                content = {
                    Text(
                        text = textPair.main,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = textPair.secondary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                },
                measurePolicy = { measurables, constraints ->
                    val layoutMaxWidth = constraints.maxWidth

                    val secondaryPlaceable = measurables[1].measure(constraints.copy(maxWidth = layoutMaxWidth / 2))
                    val mainPlaceable = measurables[0].measure(constraints.copy(maxWidth = layoutMaxWidth - secondaryPlaceable.width))

                    layout(
                        width = layoutMaxWidth,
                        height = max(mainPlaceable.height, secondaryPlaceable.height)
                    ) {
                        mainPlaceable.place(x = 0, y = 0)
                        secondaryPlaceable.place(x = layoutMaxWidth - secondaryPlaceable.width, y = 0)
                    }
                }
            )
        }
    }
}

@Composable
private fun ContentWithRow(list: List<TextPair>) {
    Column(
        verticalArrangement = spacedBy(48.dp),
        modifier = Modifier
            .padding(all = 8.dp)
    ) {
        list.forEach { textPair ->
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = textPair.main,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .weight(1f)
                )
                Text(
                    text = textPair.secondary,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Layout1()
}

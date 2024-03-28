package david.composesimulation.ui.boxWithConstraints

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoxWithConstraints1(
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        var text by rememberSaveable { mutableStateOf("") }
        var fontSizeValue by rememberSaveable { mutableIntStateOf(OriginalFontSize) }
        var textChangeType by remember { mutableStateOf<TextChangeType?>(null) }

        println("fontSizeValue $fontSizeValue")

        TextField(value = text, onValueChange = {
            textChangeType = if (it.length < text.length) {
                TextChangeType.Delete
            } else {
                TextChangeType.Append
            }
            text = it
        }, textStyle = TextStyle(fontSize = fontSizeValue.sp), placeholder = {
            Text(
                text = "Type...",
                fontSize = fontSizeValue.sp,
            )
        }, colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )

        BoxWithConstraints(
            modifier = Modifier
        ) {
            println("px - minHeight ${constraints.minHeight} maxHeight ${constraints.maxHeight} minWidth ${constraints.minWidth} maxWidth ${constraints.maxWidth}")
            println("dp - minHeight ${minHeight} maxHeight ${maxHeight} minWidth ${minWidth} maxWidth ${maxWidth}")

            if (textChangeType == TextChangeType.Append) {
                textChangeType = null
                if (maxHeight < 100.dp && fontSizeValue > 17) {
                    fontSizeValue -= FontSizeChangeUnit
                }
            } else if (textChangeType == TextChangeType.Delete) {
                textChangeType = null
                if (maxHeight > 150.dp && fontSizeValue < OriginalFontSize) {
                    fontSizeValue += FontSizeChangeUnit
                }
            }
        }
    }
}

@Preview
@Composable
private fun BoxWithConstraints1Preview() {
    BoxWithConstraints1()
}

private const val OriginalFontSize = 72
private const val FontSizeChangeUnit = 8

private enum class TextChangeType { Delete, Append, }
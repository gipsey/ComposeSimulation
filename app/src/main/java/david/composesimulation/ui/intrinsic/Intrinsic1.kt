package david.composesimulation.ui.intrinsic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Intrinsic1() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Text(
            text = "Lorem",
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
        VerticalDivider()
        Text(
            text = "ipsum",
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun Intrinsic1Preview() {
    Intrinsic1()
}

package david.composesimulation.ui.intrinsic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun Intrinsic2(
) {
    Column(
        modifier = Modifier
            .padding(48.dp)
            .background(Color.Green)
            .width(IntrinsicSize.Max)
    ) {
        Text(
            text = "Lorem",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        )
        Text(
            text = "ipsum",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        )
        Text(
            text = "dolor sit amet",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun Intrinsic2Preview() {
    Intrinsic2()
}

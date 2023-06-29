package david.composesimulation.ui.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import david.composesimulation.ui.theme.ComposeSimulationTheme

@Composable
fun WronglyChangingStateWithoutRememberAndMutableState() {
    var enabled = true

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = { enabled = false },
            enabled = enabled,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "Disable me",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

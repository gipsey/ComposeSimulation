package david.composesimulation.ui.rememberupdatedstate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun CorrectlyShowElapsedSecondsInSnackbarAfterDelay(elapsedSeconds: Int) {
    Timber.d("CorrectlyShowElapsedSecondsInSnackbarAfterDelay - elapsedSeconds $elapsedSeconds")
    val snackbarHostState = remember { SnackbarHostState() }

    val elapsedSecondsValue = rememberUpdatedState(elapsedSeconds)

    LaunchedEffect(Unit) {
        delay(3000)
        snackbarHostState.showSnackbar("Elapsed seconds are ${elapsedSecondsValue.value}")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Text(
                text = elapsedSeconds.toString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
        }
    }
}

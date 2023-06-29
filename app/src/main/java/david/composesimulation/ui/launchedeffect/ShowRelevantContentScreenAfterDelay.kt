package david.composesimulation.ui.launchedeffect

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun ShowRelevantContentScreenAfterDelay(content: String, elapsedSeconds: Int) {
    Timber.d("ShowRelevantContentScreenAfterDelay - elapsedSeconds $elapsedSeconds")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val modifier =
            Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp)
                .align(Alignment.TopStart)
                .verticalScroll(rememberScrollState())

        val showRelevantContent = remember { mutableStateOf(false) }

        if (showRelevantContent.value) {
            RelevantContent(content, modifier)
        } else {
            Information(modifier)

            LaunchedEffect(Unit) {
                Timber.d("ShowRelevantContentScreenAfterDelay LaunchedEffect 1 - delay to be run")
                delay(3000)
                Timber.d("ShowRelevantContentScreenAfterDelay LaunchedEffect 2 - delay passed")
                showRelevantContent.value = true
            }
        }

        Text(
            text = elapsedSeconds.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
    }
}

@Composable
private fun RelevantContent(content: String, modifier: Modifier) {
    Text(
        text = content,
        modifier = modifier
    )
}

@Composable
private fun Information(modifier: Modifier) {
    Text(
        text = "Before we move on, please note, that this content was provided by lorem.org",
        modifier = modifier
    )
}

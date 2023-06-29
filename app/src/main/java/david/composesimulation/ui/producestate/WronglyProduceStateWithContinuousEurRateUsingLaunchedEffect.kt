package david.composesimulation.ui.producestate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import david.composesimulation.MainViewModel
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect(
    viewModel: MainViewModel,
    elapsedSeconds: Int
) {
    Timber.d("WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect - elapsedSeconds $elapsedSeconds")

    val state = remember { mutableStateOf("loading...") }
    LaunchedEffect(Unit) {
        while (true) {
            Timber.d("WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect produceState - ${state.value} elapsedSeconds $elapsedSeconds")
            state.value = viewModel.getCurrentEurRate()
            delay(3_000)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Timber.d("WronglyProduceStateWithContinuousEurRateUsingLaunchedEffect Box - ${state.value} elapsedSeconds $elapsedSeconds")
        RelevantContent(state.value)

        Text(
            text = elapsedSeconds.toString(),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
    }
}

@Composable
private fun BoxScope.RelevantContent(content: String) {
    Text(
        text = content,
        modifier =
        Modifier
            .padding(top = 24.dp)
            .padding(horizontal = 24.dp)
            .align(Alignment.TopStart)
            .verticalScroll(rememberScrollState())
    )
}

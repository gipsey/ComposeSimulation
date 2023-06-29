package david.composesimulation.ui.derivedstateof

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun WrongEmailWithEndOfEmailReachedListener(
    body: String,
    endOfEmailReached: (Context) -> Unit
) {
    val scrollState = rememberScrollState()

    Timber.d(
        "WrongEmailWithEndOfEmailReachedListener 1 - " +
                "scrollState.value ${scrollState.value} " +
                "scrollState.maxValue ${scrollState.maxValue}"
    )

    val endOfEmailReachedValue = remember { mutableStateOf(false) }

    if (!endOfEmailReachedValue.value) {
        if (scrollState.value == scrollState.maxValue) {
            Timber.d("WrongEmailWithEndOfEmailReachedListener if")
            endOfEmailReachedValue.value = true
            endOfEmailReached(LocalContext.current)
        }
    }

    Text(
        text = body,
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(24.dp)
    )
}

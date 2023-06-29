package david.composesimulation.ui.launchedeffect

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun LaunchedEffectEmailWithMarkAsRead(
    body: String,
    elapsedSeconds: Int,
    markAsReadTimeReached: (Context) -> Unit
) {
    Timber.d("LaunchedEffectEmailWithMarkAsRead 1 - elapsedSeconds $elapsedSeconds")
    val context = LocalContext.current

    LaunchedEffect(body) {
        Timber.d("LaunchedEffectEmailWithMarkAsRead LaunchedEffect 1 - delay to be run")
        delay(3000)
        Timber.d("LaunchedEffectEmailWithMarkAsRead LaunchedEffect 2 - delay passed")
        markAsReadTimeReached(context)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        RelevantContent(body)

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

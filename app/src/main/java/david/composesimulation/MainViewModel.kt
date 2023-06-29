package david.composesimulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val elapsedSeconds = _counter.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _counter.value += 1
            }
        }
    }

    suspend fun getCurrentEurRate() : String {
        delay(2000)
        return "1 EUR ${Random.nextDouble(365.0, 400.0).toFloat()} Ft"
    }
}

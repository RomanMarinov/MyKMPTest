package presentation.ui.outdoor_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.OutdoorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
//import moe.tlaster.precompose.viewmodel.ViewModel
//import moe.tlaster.precompose.viewmodel.viewModelScope
import presentation.ui.outdoor_screen.model.OutdoorUiState

class OutdoorScreenViewModel(
    private val outdoorRepository: OutdoorRepository
) : ViewModel() {

    private var _outDoorsUiState: MutableStateFlow<OutdoorUiState> = MutableStateFlow(OutdoorUiState(emptyList()))
    val outDoorsUiState: StateFlow<OutdoorUiState> = _outDoorsUiState

    init {
        getOutdoors()
    }

    private fun getOutdoors() {
        viewModelScope.launch(Dispatchers.IO) {
            val outDoors = outdoorRepository.getOutdoors()
            _outDoorsUiState.update {
                it.copy(outdoors = outDoors)
            }
        }
    }

}
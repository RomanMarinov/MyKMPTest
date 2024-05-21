package presentation.ui.domofon_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.DomofonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.ui.domofon_screen.model.SputnikUiState

class DomofonScreenViewModel(
    private val domofonRepository: DomofonRepository
) : ViewModel() {

    private var _sputnikUiState: MutableStateFlow<SputnikUiState> = MutableStateFlow(SputnikUiState(emptyList()))
    val sputnikUiState: StateFlow<SputnikUiState> = _sputnikUiState

    init {
        getSputnik()
    }

    private fun getSputnik() {
        viewModelScope.launch {
            val res = domofonRepository.getDomofon()
            _sputnikUiState.update {
                it.copy(sputnik = res)
            }
        }
    }
}
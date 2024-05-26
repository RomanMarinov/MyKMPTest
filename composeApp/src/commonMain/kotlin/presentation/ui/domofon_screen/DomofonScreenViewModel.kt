package presentation.ui.domofon_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import domain.model.user_info.Domofon
import domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import presentation.ui.domofon_screen.model.SputnikUiState

class DomofonScreenViewModel(
//    private val domofonRepository: DomofonRepository
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    private var _domofonUiState: MutableStateFlow<SputnikUiState?> = MutableStateFlow(
        SputnikUiState(Domofon(emptyList(), emptyList())))
    val domofonUiState: StateFlow<SputnikUiState?> = _domofonUiState

    init {
        getSputnik()
    }

    private fun getSputnik() {
        viewModelScope.launch {
            val domofons: Domofon? = userInfoRepository.getUserInfo().data.domofon

            Logger.d("4444 sputnikUiState=" + domofons?.sputnik)

            domofons?.let { domof ->
                _domofonUiState.update {
                    it?.copy(domofon = domof)
                }
            }
        }
    }
}
package presentation.ui.outdoor_screen

import co.touchlab.kermit.Logger
import domain.model.Dvr
import domain.repository.OutdoorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.IO
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
//import org.koin.core.component.KoinComponent

class OutdoorScreenViewModel(
    private val outdoorRepository: OutdoorRepository
) : ViewModel() {

    private var _outDoors: MutableStateFlow<List<Dvr>> = MutableStateFlow(listOf())
    val outDoors: StateFlow<List<Dvr>> = _outDoors

    init {
        getOutdoors()
    }

    private fun getOutdoors() {


        viewModelScope.launch(Dispatchers.IO) {
            val res = outdoorRepository.getOutdoors()
           // Logger.d {" 4444 OutdoorScreenViewModel getOutdoors res=" + res }

            _outDoors.value = res
        }
    }

}
package presentation.ui.map_screen



import data.public_info.remote.dto.Data
import data.public_info.remote.dto.MarkerCityCam
import domain.repository.PublicInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class MapScreenViewModel(
    private val publicInfoRepository: PublicInfoRepository
) : ViewModel() {

    private var _cityCams: MutableStateFlow<List<MarkerCityCam>?> = MutableStateFlow(null)
    val cityCams: StateFlow<List<MarkerCityCam>?> = _cityCams


    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = publicInfoRepository.getPublicInfo()
            _cityCams.value = res.mapMarkers?.cityCams?.markerCityCams
        }
    }
}
package presentation.ui.map_screen


import co.touchlab.kermit.Logger
import data.public_info.remote.dto.Data
import data.public_info.remote.dto.Location
import data.public_info.remote.dto.MarkerCityCam
import domain.repository.PublicInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import presentation.ui.map_screen.model.GeoPointScreen
import presentation.ui.map_screen.model.MapCategory

class MapScreenViewModel(
    private val publicInfoRepository: PublicInfoRepository,
) : ViewModel() {

    private var _locationsTitle: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val locationsTitle: StateFlow<List<String>> = _locationsTitle

    private var _publicInfo: MutableStateFlow<Data?> = MutableStateFlow(null)
    val publicInfo: StateFlow<Data?> = _publicInfo

    private var _cityCams: MutableStateFlow<List<MarkerCityCam>?> = MutableStateFlow(null)
    val cityCams: StateFlow<List<MarkerCityCam>?> = _cityCams

    private var _mapCategories: MutableStateFlow<List<MapCategory>> = MutableStateFlow(emptyList())
    val mapCategories: StateFlow<List<MapCategory>> = _mapCategories


    private var _setLocation: MutableStateFlow<Location?> = MutableStateFlow(null)
    val setLocation: StateFlow<Location?> = _setLocation

    private var _spinnerCityPosition: MutableStateFlow<Int> = MutableStateFlow(0)
    val spinnerCityPosition: StateFlow<Int> = _spinnerCityPosition

//    private var _zoomNew: MutableStateFlow<Double?> = MutableStateFlow(null)
//    val zoomNew: SharedFlow<Double?> = _zoomNew.asSharedFlow()
    private val _zoomNew: MutableSharedFlow<Double?> = MutableSharedFlow(replay = 1)
    val zoomNew: SharedFlow<Double?> = _zoomNew.asSharedFlow()

    private var _geoPointZoom: MutableStateFlow<GeoPointScreen?> = MutableStateFlow(null)
    val geoPointZoom: StateFlow<GeoPointScreen?> = _geoPointZoom

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val publicInfo = publicInfoRepository.getPublicInfo()
            _publicInfo.value = publicInfo

            _cityCams.value = publicInfo.mapMarkers?.cityCams?.markerCityCams


            getMapCategories(publicInfo = publicInfo)
            getLocationTitle()
        }
    }

    private fun getMapCategories(publicInfo: Data) {

        val mapCategoriesTemp: MutableList<MapCategory> = mutableListOf()

       // val mapCategories = publicInfoRepository.getPublicInfo()
        publicInfo.mapMarkers?.let { mapMarkers ->

            mapCategoriesTemp.add(
                0, MapCategory(
                    title = mapMarkers.cityCams.title,
                    count = mapMarkers.cityCams.count,
                    isSelected = true
                )
            )
            mapCategoriesTemp.add(
                1, MapCategory(
                    title = mapMarkers.outdoorCams.title,
                    count = mapMarkers.outdoorCams.count,
                    isSelected = false
                )
            )
            mapCategoriesTemp.add(
                2, MapCategory(
                    title = mapMarkers.domofonCams.title,
                    count = mapMarkers.domofonCams.count,
                    isSelected = false
                )
            )
            mapCategoriesTemp.add(
                3, MapCategory(
                    title = mapMarkers.officeCams.title,
                    count = mapMarkers.officeCams.count,
                    isSelected = true
                )
            )

            _mapCategories.value = mapCategoriesTemp
        }
    }

    fun selectedCategory(mapCategory: MapCategory) {
        val mapCategoryFlow = flow { emit(mapCategory) }
        viewModelScope.launch(Dispatchers.IO) {
            val combineMapCategory: Flow<List<MapCategory>> =
                combine(_mapCategories, mapCategoryFlow) { mapCat, mapCatSel ->
                    mapCat.map { item ->
                        if (item == mapCatSel) {
                            mapCatSel // Заменяем элемент на mapCategorySelection, если он совпадает
                        } else {
                            item // Новый список без изменений, если элементы не совпадают
                        }
                    }
                }
            combineMapCategory.collect {
                _mapCategories.value = it
            }
        }
    }

    fun setMapLocation(position: Int) {
        Logger.d {"4444  MapViewModel onClick position=" + position }
        _publicInfo.value?.locations?.forEach { item ->
            Logger.d {"4444  MapViewModel onClick item=" + item }

            val titleToMatch = _locationsTitle.value.getOrNull(position) // Получаем title по индексу position
            if (titleToMatch != null) {
                if (titleToMatch.contains(item.title)) {
                    _setLocation.value = Location(
                        camerasAvailable = item.camerasAvailable,
                        center = item.center,
                        leftTopLat = item.leftTopLat,
                        leftTopLon = item.leftTopLon,
                        location = item.location,
                        rightBottomLat = item.rightBottomLat,
                        rightBottomLon = item.rightBottomLon,
                        title = titleToMatch,
                        titlePrefix = item.titlePrefix
                    )
                }
            }
        }
    }

    private fun getLocationTitle() {

        val list: MutableList<String> = mutableListOf()
        val data = _publicInfo.value?.locations

        data?.let {
            it.map { location ->
                list.add(location.titlePrefix + " " + location.title)
            }
        }

        list.sortedBy { !it.startsWith("рп") }
            .sortedBy { !it.startsWith("г") }
            .also { listSort ->
                _locationsTitle.value = listSort
            }


        _locationsTitle.value.forEachIndexed { index, item ->
            if (item.contains("Вологда")) {
                _spinnerCityPosition.value = index
            }
            return@forEachIndexed
        }
    }

    // для хелпа
    suspend fun setIndexSpinner(selectedCity: String) {
        _locationsTitle.collect {
            it.forEachIndexed { index, item ->
                if (item.contains(selectedCity)) {
                    Logger.d {"4444 setIndexSpinner selectedCity=" + selectedCity + " index=" + index }
                    _spinnerCityPosition.value = index
                }
                return@forEachIndexed
            }
            return@collect
        }
    }
}
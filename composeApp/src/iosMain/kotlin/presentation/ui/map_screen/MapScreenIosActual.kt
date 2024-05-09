package presentation.ui.map_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import co.touchlab.kermit.Logger
import data.public_info.remote.dto.Location
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.objcPtr
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_favorite
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.MapKit.MKAnnotationView
import platform.MapKit.MKAnnotationViewDragState
import platform.MapKit.MKCoordinateRegionMake
import platform.MapKit.MKCoordinateSpanMake
import platform.MapKit.MKMapView
import platform.MapKit.MKMapViewDelegateProtocol
import platform.MapKit.MKUserLocation
import platform.MapKit.MKUserTrackingMode
import platform.QuartzCore.CADisplayLink
import platform.darwin.NSObject
import presentation.ui.map_screen.model.MarkerDetail
import util.ColorCustomResources
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.min

var mapViewGlobal: MKMapView? = null

fun calculateZoomLevel(centerCoordinateDistance: Double): Double {
    val equatorLength = 40075016.686 // Длина экватора в метрах
    val zoomMax = 19.0 // Максимальное значение zoom level

    // Формула для вычисления zoom level
    val zoomLevel = log2(equatorLength / (centerCoordinateDistance * 360.0))

    return if (zoomLevel < 0) 0.0 else min(zoomLevel, zoomMax)
}


//class MVD() : NSObject(), MKMapViewDelegateProtocol {
//
//    @OptIn(ExperimentalForeignApi::class, ExperimentalResourceApi::class)
//    override fun mapView(mapView: platform.MapKit.MKMapView, viewForAnnotation: platform.MapKit.MKAnnotationProtocol) {
//        val location = CLLocationCoordinate2DMake(51.0, 0.0)
//        val annotation =
//            MKPointAnnotation(
//                location,
//                title = "Marker",
//                subtitle = "marker"
//            )
//
//        val annotationView = MKAnnotationView(annotation = annotation, reuseIdentifier = "customAnnotation")
//
//        val image = UIImage("star.fill")
//        annotationView.image = image
//       // return annotationView
//    }
//}

@OptIn(ExperimentalForeignApi::class)
@Composable
fun MapScreenIosActual(
    viewModel: MapScreenViewModel = koinInject(),
    paddingValue: PaddingValues,
    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
) {
    SetLocalLifecycleOwner()


//    // Получение CPointer для объекта mapViewGlobal
//    val mapViewPointer: CPointer<MKMapView> = mapViewGlobal.
//
//    val scope = rememberCoroutineScope()
//    mapViewGlobal?.region?.getPointer(scope)?.pointed?.span

    val setLocation by viewModel.setLocation.collectAsState()
    val locations by viewModel.locationsTitle.collectAsStateWithLifecycle()
    val mapCityCams by viewModel.mapCityCams.collectAsStateWithLifecycle()

    var showSomething by remember { mutableStateOf(false) }
    //var mapViewGlobal: MKMapView? by remember { mutableStateOf(null) }


    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {

//            UIKitView(
//                modifier = Modifier.fillMaxSize(),
//                factory = {
//                    MKMapView().apply {
//
//                        setDelegate(MKMapViewDelegateProtocol {
//
//                        })
//
//                        setDelegate(MKMapViewDelegateProtocol { annotation ->
//                            annotation?.title?.let { title ->
//                                val item = items.find { it.title == title }
//                                onItemClicked(item)
//                            } ?: onItemClicked(null)
//                        })
//
//
//
//
//                    }
//                },
//                update = {
//
//
//
////                    val pins = items.map { item ->
////                        val pin = MKPointAnnotation()
////                        val coordinates = item.coordinates
////
////                        pin.setCoordinate(CLLocationCoordinate2DMake(coordinates.latitude.coordinate, coordinates.longitude.coordinate))
////                        pin.setTitle(vendingMachine.address)
////                        pin
////                    }
////                    it.addAnnotations(pins)
//                }
//            )





            UIKitView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    val mkMapView = MKMapView()

                    val center = CLLocationCoordinate2DMake(
                        latitude = 59.222340,
                        longitude = 39.882431
                    )

                    val span = MKCoordinateSpanMake(
                        latitudeDelta = 20.0, // Примерное значение для широты
                        longitudeDelta = 20.0// Примерное значение для долготы
                    )

                    val region = MKCoordinateRegionMake(
                        center,
                        span = span
                    )

                    mkMapView.setRegion(region, animated = true)
                   // mkMapView.setCameraZoomRange(MKMapCameraZoomRange(20.0, 9.09))
                    //mkMapView.setCenterCoordinate(center)

                    mkMapView.setScrollEnabled(true)
                    mkMapView.setRotateEnabled(true)
                    mkMapView.setZoomEnabled(true)
                    //  mkMapView.addOverlay()

                    mapViewGlobal = mkMapView


                    mkMapView
                },
                update = {




////////////////////////////////////////////
                    // леха
                    mapViewGlobal?.delegate = object : NSObject(), MKMapViewDelegateProtocol {

//                        override fun mapView(mapView: MKMapView, regionWillChangeAnimated: Boolean) {
//                            Logger.d("4444 mkMapView 1 .mapView: $mapView")
//
//                          //  super.mapView(mapView, regionWillChangeAnimated)
//                        }

                        override fun mapView(mapView: MKMapView, regionDidChangeAnimated: Boolean) {
                            Logger.d("4444 mkMapView 2 .mapView: $mapView")
                            super.mapView(mapView,regionDidChangeAnimated = true)
                        }

                    }

//
//                    func mapView(_ mapview: MKMapView, regionWillChangeAnimated anim
//                    {
//                        self.zoom(mapview: mapview)
//                    }
//
//                    func mapView(_ mapview: MKMapView, regionDidChangeAnimated animated: Bool)
//                    {
//                        self.zoom(mapview: mapview)
//                    }


//////////////////////////////////////
//                    // установка маркеров
//                    mapCityCams?.let { listMarker ->
//                        val pins = listMarker.map { item ->
//                            val pin = MKPointAnnotation()
//                            pin.setCoordinate(CLLocationCoordinate2DMake(item.latitude, item.longitude))
//                            pin.setTitle(item.title)
//                            pin
//                        }
//                        it.addAnnotations(pins)
//                    }
 ///////////////////////////////////

                    mapViewGlobal?.delegate = object : NSObject(), MKMapViewDelegateProtocol {

                        override fun mapViewWillStartLocatingUser(mapView: MKMapView) { // не работает
                            val res1 = mapView.camera.centerCoordinateDistance()
                            Logger.d("4444 mkMapView.delegate: $res1")
                        }

                        override fun mapView(
                            mapView: MKMapView,
                            didUpdateUserLocation: MKUserLocation,
                        ) { // не работает
                            val res1 = mapView.camera.centerCoordinateDistance()
                            val res2 = didUpdateUserLocation.coordinate
                            Logger.d("4444 mkMapView.delegate: $res1, Longitude:$res2")
                        }

                        override fun mapViewWillStartLoadingMap(mapView: MKMapView) { // работет после загрузки
                            showSomething = true
                            Logger.d("4444 mkMapView.delegate: $showSomething")
                        }

                        override fun mapView(
                            mapView: MKMapView,
                            annotationView: MKAnnotationView,
                            didChangeDragState: MKAnnotationViewDragState,
                            fromOldState: MKAnnotationViewDragState,
                        ) {
                            Logger.d(
                                "4444 mapViewGlobal?.annotationView: $annotationView" +
                                        " didChangeDragState=" + didChangeDragState + " fromOldState=" + fromOldState
                            )
                        }

                        override fun mapView(
                            mapView: MKMapView,
                            didChangeUserTrackingMode: MKUserTrackingMode,
                            animated: Boolean,
                        ) {
                            Logger.d(
                                "4444 mapViewGlobal?.didChangeUserTrackingMode: $didChangeUserTrackingMode" +
                                        " animated=" + animated
                            )
                        }

                    }
 /////////////////////////////////////////////////////////////////

                    val locationManager = CLLocationManager()
                    locationManager.delegate = object : NSObject(),
                        CLLocationManagerDelegateProtocol {

                        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                            val locations1 = didUpdateLocations.filterIsInstance<CLLocation>()
                            Logger.d { "4444  locationManager?.locations1=" + locations1 }
                            if (locations1.isNotEmpty()) {
                                val currentLocation = locations1.first()
                                val latitude = currentLocation.coordinate.objcPtr()
                                //val longitude = currentLocation.coordinate
//                            Logger.d("4444 Latitude: $latitude, Longitude:")
                            }
//                        val res = MKCoordinateSpan(didUpdateLocations.objcPtr()).latitudeDelta
//                        Logger.d{"4444  locationManager?.res=" + res}
                        }

                        override fun locationManagerDidChangeAuthorization(manager: CLLocationManager) {
                            Logger.d("4444 locationManagerDidChangeAuthorization: ${manager.location}:")
                        }

                        override fun locationManagerDidResumeLocationUpdates(manager: CLLocationManager) {
                            Logger.d("4444 locationManagerDidResumeLocationUpdates: ${manager.location}:")
                        }
                    }

                    locationManager.requestWhenInUseAuthorization()
                    locationManager.startUpdatingLocation()
 /////////////////////////////////////////////////////////////////
                        it.delegate = object : CADisplayLink(), MKMapViewDelegateProtocol {

                            override fun mapView(mapView: MKMapView, regionDidChangeAnimated: Boolean) {

                                val res = mapView.camera.centerCoordinateDistance()
                                val zoom = calculateZoomLevel(res)
                                Logger.d { "4444  mapViewGlobal?.zoomzoom=" + zoom }


//                        super.mapView(mapView, regionDidChangeAnimated)
                            }

                            override fun mapViewDidChangeVisibleRegion(mapView: MKMapView) {

                                val res = mapView.camera.centerCoordinateDistance()
                                val zoom = calculateZoomLevel(res)
                                Logger.d { "4444  mapViewGlobal?.zoomzoom=" + zoom }
                            }
                        }

                        it.delegate = object : NSObject(), MKMapViewDelegateProtocol {
                            override fun mapViewDidChangeVisibleRegion(mapView: MKMapView) {

                                // mapView.
                            }
                        }
 ////////////////////////////
                    val heading = it.camera().heading
                    val centerCoordinateDistance = it.camera().centerCoordinateDistance
                    val pitch = it.camera().pitch
                    Logger.d(
                        "4444 headiheadingng: ${heading}" + " centerCoordinateDistance="
                                + centerCoordinateDistance + " pitch=" + pitch
                    )

//       mapViewGlobal?.region?.getPointer()?.pointed?.span
///////////////////////////////
                    if (showSomething) {
                        // Ваш код, который нужно выполнить, когда карта загружена
                        Logger.d { "4444  mapViewGlobal?.camera=" + mapViewGlobal?.camera()?.centerCoordinate?.size }
                    }
///////////////////////////////////////////
                }
            )

            TopControl (
                locations = locations,
                viewModel = viewModel,
                setLocation = setLocation,
                paddingValue = paddingValue)

//            ZoomControl()
//
//            BottomControl(
//                paddingValue = paddingValue,
//                viewModel = viewModel,
//                context = context,
//                moveToBottomSheetMapFragment = { markerDetail ->
//                    moveToBottomSheetMapFragment(markerDetail)
//                }
//            )
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun TopControl(
    locations: List<String>,
    setLocation: Location?,
    viewModel: MapScreenViewModel,
    paddingValue: PaddingValues,
) {
    var expanded by remember { mutableStateOf(false) }
    var labelClick by remember { mutableStateOf("Вологда") }
    var indexClick by remember { mutableStateOf(-1) }
    var dropdownMenuWidth by remember { mutableStateOf(0) }
    var dropdownMenuHeight by remember { mutableStateOf(0) }
    val localDensity = LocalDensity.current

    LaunchedEffect(indexClick) {
        viewModel.setMapLocation(position = indexClick)
    }

    LaunchedEffect(setLocation) {
        setLocation?.let {
            if (it.leftTopLat.isNotEmpty() && it.leftTopLon.isNotEmpty() && it.rightBottomLat.isNotEmpty() && it.rightBottomLon.isNotEmpty()) {
                val leftTopLat: Double = it.leftTopLat.toDouble()
                val leftTopLon: Double = it.leftTopLon.toDouble()
                val rightBottomLat: Double = it.rightBottomLat.toDouble()
                val rightBottomLon: Double = it.rightBottomLon.toDouble()

                val squareABS =
                    (abs(leftTopLat - rightBottomLat) * abs(leftTopLon - rightBottomLon)) * 1000
                if (squareABS >= 8) {
                    setZoomLocation(13.0, it.center)
                } else {
                    setZoomLocation(16.0, it.center)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 4.dp, end = 2.dp)
                    .weight(1f)
                    .height(40.dp),
                //.clip(RoundedCornerShape(15)),
                onClick = {

                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = ColorCustomResources.colorTransparentItem
                ),

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_favorite),
                        contentDescription = null,
                        //  modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Избранное",
                        //  modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "2",
                        //    modifier = Modifier.weight(1f)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().weight(1f)
//                    .onGloballyPositioned { coordinates ->
//                        dropdownMenuWidth = with(localDensity) {
//                            (coordinates.size.width / density).toInt()
//                        }
//                    }
                //  horizontalAlignment = Alignment.End
            ) {

                OutlinedButton(
                    shape = RoundedCornerShape(10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.dp, end = 4.dp)
                        .onGloballyPositioned { coordinates ->
                            dropdownMenuWidth = with(localDensity) {
                                (coordinates.size.width / density).toInt()
                            }
                            dropdownMenuHeight = with(localDensity) {
                                (coordinates.size.height).toInt()
                            }

                        },
                    //   .weight(1f),
                    //.clip(RoundedCornerShape(15)),
                    onClick = {
                        expanded = !expanded
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainBlue
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = labelClick,
                            modifier = Modifier
                                //.padding(start = 16.dp)
                                .weight(1f) // Занимает все доступное пространство влево
                        )

                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = null,
                            //  modifier = Modifier.padding(end = 16.dp) // Отступ справа
                        )
                    }
                }

                DropdownMenu(
                    modifier = Modifier
                        //.padding(0.dp)
                        .height(400.dp)
                        .background(color = Color.Transparent)
                        .shadow(0.dp, shape = RoundedCornerShape(10.dp)),
                    //  .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 4.dp),
                    //  .offset(x = 2.dp),
//                    modifier = Modifier.background(color = Color.Transparent),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(2.dp, 0.dp),
                    //scrollState = ,
                    content = {
                        locations.forEachIndexed { index, label ->
                            DropdownMenuItem(
                                modifier = Modifier
                                    .background(
                                        if (indexClick == index) ColorCustomResources.colorBazaMainBlue
                                        else Color.Transparent
                                    )
                                    .width(dropdownMenuWidth.dp)
                                    .height(40.dp),
                                text = {
                                    Text(
                                        text = label,
                                        color = if (indexClick == index) Color.White else Color.Black
                                    )
                                },
                                onClick = {
                                    expanded = false
                                    labelClick = label
                                    indexClick = index
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun setZoomLocation(zoom: Double, center: String) {
    val latitude = center.substring(0, center.indexOf(",")).toDouble()
    val longitude = center.substring(center.indexOf(" ")).trim().toDouble()
    mapViewGlobal?.let {


        val coordinate = CLLocationCoordinate2DMake(
            latitude = latitude,
            longitude = longitude
        )

        val span = MKCoordinateSpanMake(
            latitudeDelta = 0.5, // Примерное значение для широты
            longitudeDelta = 0.5 // Примерное значение для долготы
        )

        val region = MKCoordinateRegionMake(
            coordinate,
            span = span
        )

// Установка региона на карте
        mapViewGlobal?.setRegion(region, animated = true)

// Установка географической точки на карту
//        mapViewGlobal?.setCenterCoordinate(CLLocationCoordinate2D(latitude, longitude), animated = true)
//
//        val res =
//        mapViewGlobal?.moveC
//        )
    }
}

//@OptIn(ExperimentalResourceApi::class)
//@Composable
//fun ZoomControl() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(end = 16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.End
//    ) {
//        Icon(
//            modifier = Modifier
//                .padding(bottom = 8.dp)
//                //.systemBarsPadding() // Добавить отступ от скрытого статус-бара
//                .size(40.dp)
//                .clip(RoundedCornerShape(50))
//                .clickable {
//                    mapViewGlobal?.let { mapView ->
//
//                        val zoomLevel = mapViewGlobal?.cameraZoomRange()
//                        val getLocal = getLocationScreen(mapView = mapView)
////                        val zoomLevel = mapView.zoomLevelDouble
////                        val getLocal = getLocationScreen(mapView = mapView)
//
//
//
//                        setZoomLocation(zoomLevel.plus(1.0), getLocal)
//                    }
//                },
//            // .clip(RoundedCornerShape(50)),
//            imageVector = vectorResource(Res.drawable.ic_map_zoom_plus),
//            contentDescription = "plus",
//
//            )
//        Icon(
//            modifier = Modifier
//                //.padding(),
//                //.systemBarsPadding() // Добавить отступ от скрытого статус-бара
//                .size(40.dp)
//                .clip(RoundedCornerShape(50))
//                .clickable {
//                    mapViewGlobal?.let { mapView ->
//                        val zoomLevel = mapView.zoomLevelDouble
//                        val getLocal = getLocationScreen(mapView = mapView)
//
//                        setZoomLocation(zoomLevel.plus(-1.0), getLocal)
//                    }
////                    zoomLevel?.let {
////                        viewModel.setZoomAfterClick(it.plus(-1.0), zoomLocation)
////                    }
////                    viewModel.clickCountZoom(getCurrentZoom(-1.0), getLocationScreen())
//                    // viewModel.clickCountZoom(-1.0)
//                },
//            // .clip(RoundedCornerShape(50)),
//            imageVector = vectorResource(Res.drawable.ic_map_zoom_minus),
//            contentDescription = "minus",
//
//            )
//    }
//}
//
//@Composable
//fun BottomControl(
//    paddingValue: PaddingValues,
//    viewModel: MapScreenViewModel,
//    context: Context,
//    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
//) {
//    val mapCategories = viewModel.mapCategories.collectAsStateWithLifecycle()
//
//    val mapCityCams by viewModel.mapCityCams.collectAsStateWithLifecycle()
//    val mapOutDoorCams by viewModel.mapOutDoorCams.collectAsStateWithLifecycle()
//    val mapDomofonCams by viewModel.mapDomofonCams.collectAsStateWithLifecycle()
//    val mapOffice by viewModel.mapOffice.collectAsStateWithLifecycle()
//
//    val lazyListState = rememberLazyListState()
//    var indexClickCityCam by remember { mutableStateOf(true) }
//    var indexClickOutdoor by remember { mutableStateOf(false) }
//    var indexClickDomofon by remember { mutableStateOf(false) }
//    var indexClickOffice by remember { mutableStateOf(true) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(bottom = 84.dp)
//            .padding(paddingValue),
//        //  .background(Color.Red),
//        verticalArrangement = Arrangement.Bottom,
//    ) {
//        LazyRow(
//            state = lazyListState,
//            // contentPadding = paddingValue
//        ) {
//            itemsIndexed(mapCategories.value) { index, item ->
//                Spacer(modifier = Modifier.width(4.dp))
//                Row(
//                    modifier = Modifier
//                        .clickable {
//                            // трабл с этого метода
//                            viewModel.onClickCategory(position = index)
//                            if (index == 0) {
//                                indexClickCityCam = !indexClickCityCam
//                            }
//                            if (index == 1) {
//                                indexClickOutdoor = !indexClickOutdoor
//                            }
//                            if (index == 2) {
//                                indexClickDomofon = !indexClickDomofon
//                            }
//                            if (index == 3) {
//                                indexClickOffice = !indexClickOffice
//                            }
//                        }
//                        .clip(RoundedCornerShape(10))
//                        .height(40.dp)
//                        .background(
//                            method(
//                                index = index,
//                                indexClickCityCam = indexClickCityCam,
//                                indexClickOutdoor = indexClickOutdoor,
//                                indexClickDomofon = indexClickDomofon,
//                                indexClickOffice = indexClickOffice
//                            )
//                        )
//                        .padding(horizontal = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        // modifier = Modifier.fillMaxWidth(),
//                        text = item.title,
//                        color = Color.White
//                    )
//                    Text(
//                        modifier = Modifier.padding(start = 16.dp),
//                        text = item.count.toString(),
//                        color = Color.Green
//                    )
//                }
//            }
//            item {
//                Spacer(modifier = Modifier.width(4.dp))
//            }
//        }
//    }
//
//
//    outdoorMarkers(
//        mapOutDoorCams = mapOutDoorCams,
//        context = context,
//        moveToBottomSheetMapFragment = { markerDetail ->
//            moveToBottomSheetMapFragment(markerDetail)
//        }
//    )
//    domofonMarkers(
//        mapDomofonCams = mapDomofonCams,
//        context = context,
//        moveToBottomSheetMapFragment = { markerDetail ->
//            moveToBottomSheetMapFragment(markerDetail)
//        }
//    )
//    cityMarkers(
//        mapCityCams = mapCityCams,
//        context = context,
//        moveToBottomSheetMapFragment = { markerDetail ->
//            moveToBottomSheetMapFragment(markerDetail)
//        }
//    )
//    officeMarkers(
//        mapOffice = mapOffice,
//        context = context,
//        moveToBottomSheetMapFragment = { markerDetail ->
//            moveToBottomSheetMapFragment(markerDetail)
//        })
//}
//
//fun cityMarkers(
//    mapCityCams: List<MarkerCityCam>?,
//    context: Context,
//    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
//) {
//    mapCityCams?.let {
//
//        removeMarkersIsNotEmpty(mapCityCams, listMarkersCityCam)
//        removeMarkersIsNotEmpty(mapCityCams, listMarkersCityTriangle)
//
//        var markerCityCam: Marker? = null
//        for (cityCam in mapCityCams ?: emptyList()) {
//            co.touchlab.kermit.Logger.d { " 4444 SetMapView android cityCam 1" }
//
//            markerCityTriangle =
//                MarkerCityTriangle(mapViewGlobal) // маркер угла должен быть первый добавлен
//            mapViewGlobal?.overlays?.add(markerCityTriangle)
//            mapViewGlobal?.invalidate() // перерисовка из главного потока // postInvalidate из фонового
//            listMarkersCityTriangle.add(markerCityTriangle)
//        }
//
//        for (cityCam in mapCityCams ?: emptyList()) {
//            co.touchlab.kermit.Logger.d { " 4444 SetMapView android cityCam 2" }
//
//            markerCityCam = MarkerCityCamera(mapViewGlobal) // маркер камеры
//
//            val geoPointCityCam = GeoPoint(cityCam.latitude, cityCam.longitude)
//            val title = cityCam.title
//
//            val iconCityCam = ContextCompat.getDrawable(
//                context,
//                R.drawable.ic_map_city_cam
//            )
//
//            setMarkerParams(
//                marker = markerCityCam,
//                geoPoint = geoPointCityCam,
//                iconId = iconCityCam,
//                title = title
//            )
//
//            //////// //markerCityTriangle = Marker(binding.mapView) // маркер угла должен быть первый добавлен
//
//            // binding.mapView.overlays.add(markerCityTriangle)
//            // маркер камер должен быть вторым установлен (т.е. поверх первого)
//            mapViewGlobal?.overlays?.add(markerCityCam)
//            mapViewGlobal?.invalidate() // перерисовка из главного потока // postInvalidate из фонового
//
//            showInfoMarkerWindow(
//                markerCityCam, cityCam,
//                moveToBottomSheetMapFragment = { markerDetail ->
//                    moveToBottomSheetMapFragment(markerDetail)
//                }
//            )
//
//            listMarkersCityCam.add(markerCityCam) // список для только для удаления маркеров markerCams
//            //  listMarkersCityTriangle.add(markerCityTriangle)
//            listCityCam.add(cityCam)
//        }
//
//        val iconTriangle = ContextCompat.getDrawable(
//            context,
//            R.drawable.ic_map_triangle
//        )
//        createMarkerTriangle(iconTriangle = iconTriangle)
//
////        val overlays: List<Overlay> = mapViewGlobal.overlays
//        // showFavoriteCityCam(overlays = overlays)
//
//        closeInfoMarkerWindow(markerCityCam)
//
//        mapViewGlobal?.let {
//            setIconMarkerTriangle(it.zoomLevelDouble)
//        }
//
//        removeMarkersIsEmpty(mapCityCams, listMarkersCityCam)
//        removeMarkersIsEmpty(mapCityCams, listMarkersCityTriangle)
//
////        removeMarkers(mapCityCams, listMarkersCityCam)
////        removeMarkers(mapCityCams, listMarkersCityTriangle)
//    }
//}
//
//@Composable
//@OptIn(ExperimentalResourceApi::class)
//fun outdoorMarkers(
//    mapOutDoorCams: List<MarkerOutdoor>?,
//    context: Context,
//    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
//) {
//    mapOutDoorCams?.let {
//
//        removeMarkersIsNotEmpty(it, listMarkersOutDoor)
//
//        var markerOutDoorCam: Marker? = null
//        for (outDoorCam in it) {
//
//            markerOutDoorCam = Marker(mapViewGlobal) // маркер камеры
//
//            val res = vectorResource(Res.drawable.ic_map_zoom_plus)
//            val geoPoint = GeoPoint(outDoorCam.latitude, outDoorCam.longitude)
//            val iconId = ContextCompat.getDrawable(context, R.drawable.ic_map_outdoor)
//            val title = outDoorCam.title
//
//            setMarkerParams(
//                marker = markerOutDoorCam,
//                geoPoint = geoPoint,
//                iconId = iconId,
//                title = title
//            )
//
//            mapViewGlobal?.overlays?.add(markerOutDoorCam)
//            mapViewGlobal?.invalidate() // перерисовка из главного потока // postInvalidate из фонового
//
//            showInfoMarkerWindow(
//                markerOutDoorCam,
//                outDoorCam,
//                moveToBottomSheetMapFragment = { markerDetail ->
//                    moveToBottomSheetMapFragment(markerDetail)
//                }
//            )
//
//            listMarkersOutDoor.add(markerOutDoorCam) // список для только для удаления маркеров markerCams
//            listOutDoorCam.add(outDoorCam)
//        }
//
//        closeInfoMarkerWindow(markerOutDoorCam)
//        //setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
//
//
//        removeMarkersIsEmpty(it, listMarkersOutDoor)
////        removeMarkers(it, listMarkersOutDoor)
//    }
//}
//
//fun domofonMarkers(
//    mapDomofonCams: List<MarkerDomofon>?,
//    context: Context,
//    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
//) {
//    mapDomofonCams?.let {
//
//        removeMarkersIsNotEmpty(it, listMarkersDomofon)
//
//        var markerDomofonCam: Marker? = null
//        for (domofonCam in it) {
//            markerDomofonCam = Marker(mapViewGlobal) // маркер камеры
//
//            val geoPoint = GeoPoint(domofonCam.latitude, domofonCam.longitude)
//            val iconId = ContextCompat.getDrawable(context, R.drawable.ic_map_domofon)
//            val title = domofonCam.title
//
//            setMarkerParams(
//                marker = markerDomofonCam,
//                geoPoint = geoPoint,
//                iconId = iconId,
//                title = title
//            )
//
//            mapViewGlobal?.overlays?.add(markerDomofonCam)
//            mapViewGlobal?.invalidate() // перерисовка из главного потока // postInvalidate из фонового
//
//            showInfoMarkerWindow(
//                markerDomofonCam,
//                domofonCam,
//                moveToBottomSheetMapFragment = { markerDetail ->
//                    moveToBottomSheetMapFragment(markerDetail)
//                }
//            )
//
//            listMarkersDomofon.add(markerDomofonCam) // список для только для удаления маркеров markerCams
//            listDomofonCam.add(domofonCam)
//        }
//
//        closeInfoMarkerWindow(markerDomofonCam)
//        //  setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
//
//        removeMarkersIsEmpty(it, listMarkersDomofon)
////        removeMarkers(it, listMarkersDomofon)
//    }
//}
//
//fun officeMarkers(
//    mapOffice: List<MarkerOffice>?,
//    context: Context,
//    moveToBottomSheetMapFragment: (MarkerDetail) -> Unit,
//) {
//    mapOffice?.let {
//
//        removeMarkersIsNotEmpty(it, listMarkersOffice)
//
//        var markerOffice: Marker? = null
//        for (office in it) {
//
//            markerOffice = Marker(mapViewGlobal) // маркер камеры
//
//            val geoPoint = GeoPoint(office.latitude, office.longitude)
//            val iconId = ContextCompat.getDrawable(context, R.drawable.ic_map_office)
//            val title = office.title
//
//            setMarkerParams(
//                marker = markerOffice,
//                geoPoint = geoPoint,
//                iconId = iconId,
//                title = title
//            )
//
//            mapViewGlobal?.overlays?.add(markerOffice)
//            mapViewGlobal?.invalidate() // перерисовка из главного потока // postInvalidate из фонового
//
//            showInfoMarkerWindow(
//                markerOffice,
//                office,
//                moveToBottomSheetMapFragment = { markerDetail ->
//                    moveToBottomSheetMapFragment(markerDetail)
//                }
//            )
//
//            listMarkersOffice.add(markerOffice) // список для только для удаления маркеров markerCams
//            listOffice.add(office)
//        }
//
//        closeInfoMarkerWindow(markerOffice)
//        //  setIconMarkerMap(binding.mapView.zoomLevel.toDouble())
//
//        removeMarkersIsEmpty(it, listMarkersOffice)
////        removeMarkers(it, listMarkersOffice)
//    }
//}

@Composable
fun SetLocalLifecycleOwner() {
    val localLifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = localLifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        Logger.d { "4444 MapScreen Lifecycle.Event.ON_START" }
                    }

                    Lifecycle.Event.ON_STOP -> { // когда свернул
                        Logger.d { "4444 MapScreen Lifecycle.Event.ON_STOP" }
                    }

                    Lifecycle.Event.ON_DESTROY -> { // когда удалил из стека
                        Logger.d { "4444 MapScreen Lifecycle.Event.ON_DESTROY" }
                    }

                    else -> {}
                }
            }
            localLifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                localLifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

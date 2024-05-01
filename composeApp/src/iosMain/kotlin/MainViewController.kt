

import androidx.compose.ui.window.ComposeUIViewController
import di.commonModule
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.context.startKoin


@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController { App()

//    var showSomething by remember { mutableStateOf(false) }
//
//    UIKitView(factory = {
//
//
//        val mkMapView = MKMapView()
//
//        mkMapView.delegate = object : MKMapViewDelegateProtocol {
//            override fun mapView(mapView: MKMapView, didDeselectAnnotation: MKAnnotationProtocol) {
//                showSomething = true
//            }
//        }
//
//        mkMapView
//    }, Modifier.fillMaxSize())
//
//    if (showSomething) {
//
//    }
}

fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}


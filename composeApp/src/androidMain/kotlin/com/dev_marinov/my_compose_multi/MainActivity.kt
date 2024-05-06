package com.dev_marinov.my_compose_multi

import App
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // https://stackoverflow.com/questions/78190854/status-bar-color-change-in-compose-multiplatform
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    Color.TRANSPARENT, Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Color.TRANSPARENT, Color.TRANSPARENT
                )
            )
            // содержимое вашего приложения располагаться за системными элементами, такими как
            // StatusBar (строка состояния) или NavigationBar (панель навигации) в Android.
            // сначала работало потом изменений не заметил
            WindowCompat.setDecorFitsSystemWindows(window, false)

            App()

            //MapView()

//            AndroidView {
//
//            }
//            OpenStreetMap {
//
//            }


            //MapScreen(this)
//            val c = LocalContext.current
//            val res = MapView(c)
//
//            // define camera state
//            val cameraState = rememberCameraState {
//                geoPoint = GeoPoint(-6.3970066, 106.8224316)
//                zoom = 12.0 // optional, default is 5.0
//            }
//
//            // add node
//            OpenStreetMap(
//                modifier = Modifier.fillMaxSize(),
//                cameraState = cameraState
//            )
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}




@Composable
fun MapScreen(context: Context) {



    //var mapView: MapView? by remember { mutableStateOf(null) }

    // Этот блок кода будет выполнен, когда MapView будет готова к использованию
    // Внутри этого блока вы можете настроить или взаимодействовать с картой
//    MapView(
//        context = context,
//        modifier = Modifier.fillMaxSize(),
//        onMapReady = { mapView ->
//            // Дополнительная настройка и взаимодействие с картой
//            mapView.controller.setZoom(15.0)
//
//            mapView.getMapCenter(
//                GeoPoint(
//                    59.222340, 39.882431
//                )
//            )
//        }
//    )
}



@Composable
fun MapView(
    context: Context,
    modifier: Modifier = Modifier,
    onMapReady: (MapView) -> Unit = {}
) {
    AndroidView(
        factory = { ctx: Context ->
            MapView(ctx).apply {
                // Дополнительная настройка карты
                setTileSource(TileSourceFactory.MAPNIK)
                // и т.д.
                onMapReady(this)
            }
        },
        modifier = modifier,
        update = {
            // it.context = context
            // Можно добавить дополнительную логику обновления
        }
    )
}


//actual fun MapView() {
//
//}
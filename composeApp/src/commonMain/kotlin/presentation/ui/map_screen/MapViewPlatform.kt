package presentation.ui.map_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable


expect class MapViewPlatform() {
    fun getMapViewManipulation(): String

    //fun setZoomLocation(zoom: Double, center: String)

    @Composable
    fun SetMapView(
        //publicInfo: Data?
        paddingValue: PaddingValues,
        viewModel: MapScreenViewModel
    )
}
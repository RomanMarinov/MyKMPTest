package presentation.ui.map_screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MapScreen(
    navigator: Navigator,
    viewModel: MapScreenViewModel = koinInject()
) {

    val cityCams by viewModel.cityCams.collectAsStateWithLifecycle()


    cityCams?.let {
        LazyColumn {
            items(it) { item ->

                KamelImage(
                    resource = asyncPainterResource(item.additionalMap.previewUrl),
                    contentDescription = "preview_url"
                )

               // Text(text = item.additionalMap.)
            }
        }
    }
}
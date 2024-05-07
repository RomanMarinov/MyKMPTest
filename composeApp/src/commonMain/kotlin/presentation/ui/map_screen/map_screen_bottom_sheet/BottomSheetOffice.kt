package presentation.ui.map_screen.map_screen_bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_map_city_cam
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import presentation.ui.map_screen.model.MarkerDetail
import util.ColorCustomResources

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BottomSheetOffice(
    navigator: Navigator,
    markerDetail: MarkerDetail,
    openBottomSheet: (Boolean) -> Unit,
){
    //Log.d("4444", " BottomSheet сработал markerDetail=" + markerDetail)
    val scope = rememberCoroutineScope()

    val openBottomSheetState by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (openBottomSheetState) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
              //  .height(500.dp),
            onDismissRequest = { openBottomSheet(false) },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 16.dp)
                            .align(Alignment.CenterStart),
                        imageVector = vectorResource(Res.drawable.ic_map_city_cam),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Офис Baza.net"
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = markerDetail.titleAddress.toString()
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "Часы работы:\n${markerDetail.worktime}"
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "Телефон:\n${markerDetail.visible}"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 16.dp),
                        imageVector = vectorResource(Res.drawable.ic_map_city_cam),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 16.dp),
                        imageVector = vectorResource(Res.drawable.ic_map_city_cam),
                        contentDescription = null
                    )
                    Icon(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 16.dp),
                        imageVector = vectorResource(Res.drawable.ic_map_city_cam),
                        contentDescription = null
                    )
                }

                OutlinedButton(
                    shape = RoundedCornerShape(10),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(40.dp),
                    //.clip(RoundedCornerShape(15)),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainRed
                    ),

                    ) {
                    Text(
                        text = "Позвонить",
                        //  modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
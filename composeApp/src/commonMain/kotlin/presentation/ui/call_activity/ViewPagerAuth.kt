package presentation.ui.call_activity

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_help_number
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerAuth(
    inputTextPhoneNumber: String,
    onInputTextPhoneNumber: (String) -> Unit,
    onMoveToMainActivity: () -> Unit,
    onMakeCall: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TabsAuth.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }
    val isShowCallContainer = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .fillMaxWidth(),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = ColorCustomResources.colorBazaMainBlue, // Здесь укажите желаемый цвет для горизонтальной линии
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                )
            }
        ) {
            TabsAuth.entries.forEachIndexed { index, currentTab ->
                Tab(
                    modifier = Modifier.background(Color.White),
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = MaterialTheme.colorScheme.outline,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(currentTab.ordinal)
                        }
                    },
                    text = {
                        Text(
                            text = currentTab.text,
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> LoginByPhoneNumber(
                    isShowCallContainer = isShowCallContainer.value,
                    onShowCallContainer = {
                        isShowCallContainer.value = it
                    },
                    inputTextPhoneNumber = inputTextPhoneNumber,
                    onInputTextPhoneNumber = {
                        onInputTextPhoneNumber(it)
                    },
                    onMoveToMainActivity = {
                        onMoveToMainActivity()
                    },
                    onMakeCall = {
                        onMakeCall()
                    }
                )

                1 -> LoginByWiFi(
                    isShowCallContainer = isShowCallContainer.value,
                    onShowCallContainer = {
                        isShowCallContainer.value = it
                    },
                    onMoveToMainActivity = {
                        onMoveToMainActivity()
                    }
                )
            }

        }
    }
}

@Composable
fun LoginByPhoneNumber(
    isShowCallContainer: Boolean,
    onShowCallContainer: (Boolean) -> Unit,
    inputTextPhoneNumber: String,
    onInputTextPhoneNumber: (String) -> Unit,
    onMoveToMainActivity: () -> Unit,
    onMakeCall: () -> Unit
//    callActivity: CallActivity
) {
    var textLastNameState by remember { mutableStateOf("") }
    var textNameState by remember { mutableStateOf("") }
    var textMiddleNameState by remember { mutableStateOf("") }
    var isFocusTextFiled by remember { mutableStateOf(false) }

    val isShowWarning = remember { mutableStateOf(false) }
    val errorTextWarning = remember { mutableStateOf("") }
    val errorLineColorClick = remember { mutableStateOf(ColorCustomResources.colorBazaMainBlue) }

    val isCallingPhone = remember { mutableStateOf(false) }

    if (!isShowCallContainer) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    //.fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp, bottom = 20.dp),
                text = "Введите номер телефона"
            )

            PhoneNumberTransformation(
                inputTextPhoneNumber = inputTextPhoneNumber,
                errorLineColorClick = errorLineColorClick.value,
                onInputTextPhoneNumber = {
                    onInputTextPhoneNumber(it)
                },
                onErrorLineColor = {
                    errorLineColorClick.value = it
                },
                onShowWarning = {
                    isShowWarning.value = it
                    errorTextWarning.value = "Только мобильные номера"
                }
            )


            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (isShowWarning.value) {
                    Text(
                        color = Color.Red,
                        text = errorTextWarning.value
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(
                    modifier = Modifier
                        // .fillMaxWidth()
                        .padding(bottom = 16.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        if (inputTextPhoneNumber.length == 10) {
                           // isShowCallContainer.value = true
                            onShowCallContainer(true)
                            //onMoveToMainActivity()
                        } else {
                            isShowWarning.value = true
                            errorTextWarning.value = "Обязательное поле для ввода"
                            errorLineColorClick.value = Color.Red
                        }

                    },
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            text = "Далее"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainBlue
                    ),
                    //shape = RoundedCornerShape(10.dp)
                )
            }
        }
    }

    ////////////////////////////////////////

    if (isShowCallContainer) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    //.fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                text = "Позвоните на бесплатный номер",
                color = Color.Black,
                fontSize = 20.sp
            )

            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = "8-800-1000-249",
                    fontSize = 30.sp
                )
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_help_number),
                    contentDescription = null
                    //  modifier = Modifier.padding(end = 16.dp) // Отступ справа
                )
            }

            Text(
                modifier = Modifier
                    //.fillMaxWidth()
                    //.align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp),
                text = "Мы определим Ваш номер\nи сразу завершим вызов"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    modifier = Modifier.weight(2f),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        onMakeCall()
                    },
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            text = "Позвонить"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainBlue
                    ),
                    //shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ElevatedButton(
                    modifier = Modifier.weight(2f),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
//                        isShowCallContainer.value = false
                        onShowCallContainer(false)
                    },
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp),
                            text = "Отмена"
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainRed
                    ),
                    //shape = RoundedCornerShape(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    if (isCallingPhone.value) {
        Dialer()
    }
}

@Composable
fun LoginByWiFi(
    isShowCallContainer: Boolean,
    onShowCallContainer: (Boolean) -> Unit,
    onMoveToMainActivity: () -> Unit,
) {
    var numberContractState by remember { mutableStateOf("") }
    var isFocusTextFiled by remember { mutableStateOf(false) }

    val isShowCallContainerState = remember { mutableStateOf(isShowCallContainer) }

    LaunchedEffect(isShowCallContainerState) {
        onShowCallContainer(isShowCallContainer)
    }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 10.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 16.dp),
            text = "Если Вы являетесь нашим абонентом, возможен вход по Вашей сети WI-FI"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            //.padding(start = 26.dp, end = 26.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            ElevatedButton(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 8.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                shape = RoundedCornerShape(8.dp),

                onClick = {
                    onMoveToMainActivity()
                },
                content = {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp),
                        text = "Войти по Wi-Fi"
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = ColorCustomResources.colorBazaMainBlue
                ),
                //shape = RoundedCornerShape(10.dp)
            )

        }
    }
}

enum class TabsAuth(
    val text: String,
) {
    TabOneAuth(text = "Вход по номеру телефона"),
    TabTwoAuth(text = "Вход по WI-FI")
}
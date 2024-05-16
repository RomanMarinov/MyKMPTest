package com.dev_marinov.my_compose_multi.ui

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.dev_marinov.my_compose_multi.CallActivity
import com.dev_marinov.my_compose_multi.MainActivity
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.call_activity_logo
import org.jetbrains.compose.resources.painterResource
import util.ColorCustomResources
import util.TextFieldHelper

@Composable
fun CallActivityContent(callActivity: CallActivity) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(3f))

        Image(
            painter = painterResource(Res.drawable.call_activity_logo),
            modifier = Modifier
                .size(width = 156.dp, height = 120.dp),
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Личный кабинет",
            fontSize = 30.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            ElevatedCard(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                ViewPagerAuth(callActivity)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(16.dp),
            text = "Версия четкая"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerAuth(callActivity: CallActivity) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { TabsAuth.entries.size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

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
                0 -> LoginByPhoneNumber(callActivity)
                1 -> LoginByWiFi(callActivity)
            }

        }
    }
}

@Composable
fun LoginByPhoneNumber(callActivity: CallActivity) {
    var textLastNameState by remember { mutableStateOf("") }
    var textNameState by remember { mutableStateOf("") }
    var textMiddleNameState by remember { mutableStateOf("") }
    var isFocusTextFiled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Введите номер телефона"
        )

        TextFieldHelper(
            value = textLastNameState,
            onValueChanged = { textLastNameState = it },
            hintText = "Фамилия"
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 30.dp, bottom = 30.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                shape = RoundedCornerShape(8.dp),

                onClick = {
                    moveToMainActivity(callActivity)
                },
                content = { Text("Далее") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = ColorCustomResources.colorBazaMainBlue
                ),
                //shape = RoundedCornerShape(10.dp)
            )
        }

    }
}

@Composable
fun LoginByWiFi(callActivity: CallActivity) {
    var numberContractState by remember { mutableStateOf("") }
    var isFocusTextFiled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = "Если Вы являетесь нашим абонентом, возможен вход по Вашей сети WI-FI"
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            ElevatedButton(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(start = 26.dp, end = 26.dp, top = 30.dp, bottom = 30.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                shape = RoundedCornerShape(8.dp),

                onClick = {
                    moveToMainActivity(callActivity)
                },
                content = { Text("Войти по Wi-Fi") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = ColorCustomResources.colorBazaMainBlue
                ),
                //shape = RoundedCornerShape(10.dp)
            )

        }
    }
}

fun moveToMainActivity(callActivity: CallActivity) {
    val intent = Intent(callActivity, MainActivity::class.java)
    callActivity.startActivity(intent)
}

enum class TabsAuth(
    val text: String,
) {
    TabOneAuth(text = "Вход по номеру телефона"),
    TabTwoAuth(text = "Вход по WI-FI")
}
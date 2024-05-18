import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.call_activity_logo
import org.jetbrains.compose.resources.painterResource
import util.ColorCustomResources

@Composable
fun CallActivityContent(
    onMoveToMainActivity: () -> Unit,
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .hideKeyboardOnOutsideClick() // убираем клаву
            .pointerInput(Unit) { // снимаем фокус
                detectTapGestures { focusManager.clearFocus() }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(3f))

        Image(
            painter = painterResource(Res.drawable.call_activity_logo),
            modifier = Modifier
                .padding(top = 50.dp)
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
                ViewPagerAuth(
                    onMoveToMainActivity = {
                        onMoveToMainActivity()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(16.dp).imePadding(),
            text = "Версия четкая"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPagerAuth(
    onMoveToMainActivity: () -> Unit,
) {
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
                0 -> LoginByPhoneNumber(
                    onMoveToMainActivity = {
                        onMoveToMainActivity()
                    }
                )

                1 -> LoginByWiFi(
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
    onMoveToMainActivity: () -> Unit,
//    callActivity: CallActivity
) {
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
                //.fillMaxWidth()
                //.align(Alignment.CenterHorizontally)
                .padding(top = 20.dp, bottom = 20.dp),
            text = "Введите номер телефона"
        )

//        TextFieldHelper(
//            value = textLastNameState,
//            onValueChanged = { textLastNameState = it },
//            hintText = "Номер запили"
//        )

//        TutorialInputMaskScreen()
        TutorialManualVisualTransformation()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ElevatedButton(
                modifier = Modifier
                    // .fillMaxWidth()
                    .padding(top = 30.dp, bottom = 16.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),
                shape = RoundedCornerShape(8.dp),

                onClick = {
                    onMoveToMainActivity()
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

@Composable
fun LoginByWiFi(
    onMoveToMainActivity: () -> Unit,
) {
    var numberContractState by remember { mutableStateOf("") }
    var isFocusTextFiled by remember { mutableStateOf(false) }

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

//fun moveToMainActivity(callActivity: CallActivity) {
//
//
//
//
//    val intent = Intent(callActivity, MainActivity::class.java)
//    callActivity.startActivity(intent)
//}

enum class TabsAuth(
    val text: String,
) {
    TabOneAuth(text = "Вход по номеру телефона"),
    TabTwoAuth(text = "Вход по WI-FI")
}

@Composable
fun TutorialManualVisualTransformation() {
    class ManualVisualTransformation(val enableCursorMove: Boolean) : VisualTransformation {
        // Этот метод преобразует входной текст в форматированный текст согласно маске.
        override fun filter(text: AnnotatedString): TransformedText {
            val inputText = text.text
            Logger.d{"4444 inputText=" + inputText}
            // "+7 (XXX)-XXX-XX-XX" // моя маска 15 символов с пробелами
            val formattedText = when (inputText.length) {

                in (1..3) -> "+7 ($inputText"
                in (4..6) -> "+7 (${inputText.substring(0, 3)})-${inputText.substring(3)}"
                in (7..8) -> "+7 (${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6)}"
                in (9..10) -> "+7 (${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6, 8)}-${inputText.substring(8)}"





//                in (1..3) -> "($inputText"
//                in (4..6) -> "(${inputText.substring(0, 3)})-${inputText.substring(3)}"
//                in (7..8) -> "(${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6)}"
//                in (9..10) -> "(${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6, 8)}-${inputText.substring(8)}"
                else -> ""
            }

            val offsetTranslator = object : OffsetMapping {
                // Преобразует позицию курсора вперед из оригинального текста в позицию курсора в форматированном тексте.
                // offset: Int - позиция курсора в оригинальном тексте.
                // Int - позиция курсора в форматированном тексте.
                override fun originalToTransformed(offset: Int): Int {
                    // offset = input.count/original at cursor position -- "(11) 1|" offset = 3

                    Logger.d{"4444 originalToTransformed offset=" + offset}
                    val transformedCursor = when (offset) {
                        in (1..3) -> offset + 4 // "(XX|" offset = 2, sep = 1, result = 3
                        in (4..6) -> offset + 6 // "(XX) X|" offset = 3, sep = 3, result = 6
                        in (7..8) -> offset + 7
                        in (9..10) -> offset + 8
                        else -> offset
                    }

                    // Logger.d{"4444 originalToTransformed transformedCursor=" + transformedCursor}
                    return if (enableCursorMove) transformedCursor // formattedText
                    else formattedText.length // "+7 (XXX)-XXX-XX-XX" return 17
                }

                // Преобразует позицию курсора при клике из форматированного текста в позицию курсора в оригинальном тексте.
                // offset: Int - позиция курсора в форматированном тексте.
                // Int - позиция курсора в оригинальном номере телефона (на Х-ах).
                override fun transformedToOriginal(offset: Int): Int {
                    val originalCursor = when (offset) {
                        in (4..7) -> offset - 4
                        in (9..12) -> offset - 6
                        in (13..15) -> offset - 7
                        in (16..18) -> offset - 8 // надо здесь
                        else -> offset // "+7 (XXX)-XXX-XX-XX" 18 элементов
                    }

                      Logger.d{"4444 originalCursor=" + originalCursor}

                    return if (enableCursorMove) originalCursor // inputs
                    else inputText.length // "(XXX) - XXX - XX - XX" return 10
                }
            }
            return TransformedText(AnnotatedString(formattedText), offsetTranslator)
        }
    }

    var inputText by remember { mutableStateOf("") }
    val mask = "+7 (XXX)-XXX-XX-XX"
    val maskCharInput = 'X'
    val inputMaxLength = mask.count { maskChar -> maskChar == maskCharInput }

    val focusRequester = remember { FocusRequester() }

    val interactionSource = remember { MutableInteractionSource() }

    Logger.d{" 4444 inputText=" + inputText}

    TextField(
        interactionSource = interactionSource,
        colors = TextFieldDefaults.colors(
            cursorColor = ColorCustomResources.colorBazaMainBlue,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            unfocusedIndicatorColor = ColorCustomResources.colorBazaMainBlue,
            focusedIndicatorColor = ColorCustomResources.colorBazaMainBlue,
//            unfocusedLeadingIconColor = ColorCustomResources.colorBazaMainBlue

        ),
        textStyle = TextStyle.Default.copy(fontSize = 28.sp),
        modifier = Modifier
            .background(Color.White)
            .focusRequester(focusRequester = focusRequester),
        value = inputText,
        onValueChange = { currentText ->
            inputText = currentText.take(inputMaxLength).filter { it.isDigit() }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = ManualVisualTransformation(enableCursorMove = true)
    )
}

////////////////////////////////////////////////////////////////////////
//@Composable
//fun TutorialManualVisualTransformation() {
//    class ManualVisualTransformation(val enableCursorMove: Boolean) : VisualTransformation {
//        // Этот метод преобразует входной текст в форматированный текст согласно маске.
//        override fun filter(text: AnnotatedString): TransformedText {
//            val inputText = text.text
//            // "(XXX)-XXX-XX-XX" // моя маска 15 символов с пробелами
//            val formattedText = when (inputText.length) {
//                in (1..3) -> "($inputText"
//                in (4..6) -> "(${inputText.substring(0, 3)})-${inputText.substring(3)}"
//                in (7..8) -> "(${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6)}"
//                in (9..10) -> "(${inputText.substring(0, 3)})-${inputText.substring(3, 6)}-${inputText.substring(6, 8)}-${inputText.substring(8)}"
//                else -> ""
//            }
//
//            val offsetTranslator = object : OffsetMapping {
//                // Преобразует позицию курсора вперед из оригинального текста в позицию курсора в форматированном тексте.
//                // offset: Int - позиция курсора в оригинальном тексте.
//                // Int - позиция курсора в форматированном тексте.
//                override fun originalToTransformed(offset: Int): Int {
//                    // offset = input.count/original at cursor position -- "(11) 1|" offset = 3
//
//                    Logger.d{"4444 originalToTransformed offset=" + offset}
//                    val transformedCursor = when (offset) {
//                        in (1..3) -> offset + 1 // "(XX|" offset = 2, sep = 1, result = 3
//                        in (4..6) -> offset + 3 // "(XX) X|" offset = 3, sep = 3, result = 6
//                        in (7..8) -> offset + 4
//                        in (9..10) -> offset + 5
//                        else -> offset
//                    }
//
//                   // Logger.d{"4444 originalToTransformed transformedCursor=" + transformedCursor}
//                    return if (enableCursorMove) transformedCursor // formattedText
//                    else formattedText.length // "(XX) XXXXX - XXXX" return 17
//                }
//
//                // Преобразует позицию курсора при клике из форматированного текста в позицию курсора в оригинальном тексте.
//                // offset: Int - позиция курсора в форматированном тексте.
//                // Int - позиция курсора в оригинальном номере телефона (на Х-ах).
//                override fun transformedToOriginal(offset: Int): Int {
//                    val originalCursor = when (offset) {
//                        in (1..4) -> offset - 1
//                        in (6..9) -> offset - 3
//                        in (10..12) -> offset - 4
//                        in (13..15) -> offset - 5 // надо здесь
//                        else -> offset // "(XXX)-XXX-XX-XX"
//                    }
//
//                  //  Logger.d{"4444 originalCursor=" + originalCursor}
//
//                    return if (enableCursorMove) originalCursor // inputs
//                    else inputText.length // "(XXX) - XXX - XX - XX" return 10
//                }
//            }
//            return TransformedText(AnnotatedString(formattedText), offsetTranslator)
//        }
//    }
//
//    var inputText by remember { mutableStateOf("") }
//    val mask = "(XXX)-XXX-XX-XX"
//    val maskCharInput = 'X'
//    val inputMaxLength = mask.count { maskChar -> maskChar == maskCharInput }
//
//    val focusRequester = remember { FocusRequester() }
//
//    val interactionSource = remember { MutableInteractionSource() }
//
//    Logger.d{" 4444 inputText=" + inputText}
//
//    TextField(
//        interactionSource = interactionSource,
//        colors = TextFieldDefaults.colors(
//            cursorColor = ColorCustomResources.colorBazaMainBlue,
//            focusedContainerColor = Color.White,
//            unfocusedContainerColor = Color.White,
//            focusedTextColor = Color.Black,
//            unfocusedTextColor = Color.Black,
//
//            unfocusedIndicatorColor = ColorCustomResources.colorBazaMainBlue,
//            focusedIndicatorColor = ColorCustomResources.colorBazaMainBlue,
////            unfocusedLeadingIconColor = ColorCustomResources.colorBazaMainBlue
//
//        ),
//        textStyle = TextStyle.Default.copy(fontSize = 28.sp),
//        modifier = Modifier
//            .background(Color.White)
//            .focusRequester(focusRequester = focusRequester),
//        value = inputText,
//        onValueChange = { currentText ->
//            inputText = currentText.take(inputMaxLength).filter { it.isDigit() }
//        },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//        visualTransformation = ManualVisualTransformation(enableCursorMove = true)
//    )
//}
/////////////////////////////////////////////////

fun Modifier.hideKeyboardOnOutsideClick(): Modifier = composed {
    val controller = LocalSoftwareKeyboardController.current
    this then Modifier.noRippleClickable {
        controller?.hide()
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this then Modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

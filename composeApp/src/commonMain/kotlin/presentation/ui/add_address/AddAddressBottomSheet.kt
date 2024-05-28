package presentation.ui.add_address

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import co.touchlab.kermit.Logger
import domain.add_address.AddAddressResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_close
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import util.ColorCustomResources
import util.ScreenRoute


private val REGEX_ADDRESS = ".*,.*,.*".toRegex()
private val REGEX_APT = "^\\d*".toRegex()

enum class CheckData {
    APPROVED, NOT_APPROVED, DEFAULT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressBottomSheet(
    fromScreen: String,
    openBottomSheet: (Boolean) -> Unit,
    navHostController: NavHostController
) {
    val openBottomSheetState by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val interactionSource = remember { MutableInteractionSource() }
    val inputTextPhoneNumber = remember { mutableStateOf("") }
    if (openBottomSheetState) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
//            containerColor = Color.White, не
//            contentColor = Color.White,
            onDismissRequest = { openBottomSheet(false) },
            sheetState = bottomSheetState,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()

                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp),
//                ) {
//                    TopTitle(
//                        openBottomSheet = {
//                            openBottomSheet(it)
//                        }
//                    )
                AutoComplete(
                    fromScreen = fromScreen,
                    navHostController = navHostController
                )
                //  }

//                AddAddressTransformation(
//                    inputTextPhoneNumber = inputTextPhoneNumber.value,
//                    errorLineColorClick = Color.Red,
//                    onInputTextPhoneNumber = {
//                        inputTextPhoneNumber.value = it
//                    },
//                    onErrorLineColor = {},
//                    onShowWarning = {}
//                )
            }
        }
    }
}

@Composable
fun TopTitle(
    openBottomSheet: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            modifier = Modifier
                .padding(end = 16.dp)
                .size(34.dp),
            //    .align(Alignment.CenterEnd)
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(containerColor = ColorCustomResources.colorBackgroundClose),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        openBottomSheet(false)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    // close
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_close),
                    contentDescription = null,
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete(
    fromScreen: String,
    viewModel: AddAddressViewModel = koinInject(),
    navHostController: NavHostController
) {
    val addresses by viewModel.addresses.collectAsStateWithLifecycle()
    val errorNetwork by viewModel.errorNetwork.collectAsStateWithLifecycle()
    val addAddressResponse by viewModel.addAddressResponse.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    var addressText by remember { mutableStateOf("") }
    var flatNumberText by remember { mutableStateOf("") }

    var positionDropList by remember { mutableIntStateOf(-1) }

    var isFocusedAddress by remember { mutableStateOf(false) }
    var isFocusedFlatNumber by remember { mutableStateOf(false) }

    val addressFocusRequester = remember { FocusRequester() }
    val flatNumberFocusRequester = remember { FocusRequester() }

    val isExecuteRequestFocus = remember { mutableStateOf(false) }

    var expandedAddresses by remember { mutableStateOf(false) }
    val selectedItemLength = remember { mutableStateOf(-1) }

    //  val isMatchesREGEX = remember { mutableStateOf(false) }
    val isClickAddressTextField = remember { mutableStateOf(false) }

    val isShowTextFieldAndButton = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val isApprovedData = remember { mutableStateOf(CheckData.DEFAULT) }

    val isDvrOrDomofonAvailable = remember { mutableStateOf(false) }

    LaunchedEffect(addressText) {
        Logger.d("4444 addressText=" + addressText)
        if (addressText.length <= 2) { // срабатывает каждый раз когда меньше 3 символов набрано
            expandedAddresses = false
            isShowTextFieldAndButton.value = false
            isExecuteRequestFocus.value = false
        } else { // срабатывает каждый раз когда больше 3 символов набрано
            expandedAddresses = true // открыть список

            viewModel.checkAddress(addressText = addressText) // долбит запрос на каждой новой букве
            if (addressText.matches(REGEX_ADDRESS)) { // момент выбора из выподающего списка
                Logger.d("4444 matches сработал")
                Logger.d("4444 попытка закрыть выпадающий список")
                expandedAddresses = false // закрыть список
                // isShowTextFieldAndButton.value = true
                //  flatNumberFocusRequester.requestFocus()
                // isMatchesREGEX.value = true
                if (selectedItemLength.value != addressText.length) {
                    isShowTextFieldAndButton.value = false
                    isExecuteRequestFocus.value = false
                } else {
                    isShowTextFieldAndButton.value = true
                }
            } else {

                // isMatchesREGEX.value = false
            }
        }
    }

//    LaunchedEffect(selectedItemLength.value) {
//        if (selectedItemLength.value != addressText.length) {
//            isShowTextFieldAndButton.value = false
//        }
//    }

    LaunchedEffect(isFocusedAddress) {
        if (addressText.length > 2 && isFocusedAddress) {
            Logger.d("4444 попытка попытка открыть выпадающий список по клику")
            isShowTextFieldAndButton.value = false
            expandedAddresses = true
        }
    }

    LaunchedEffect(isExecuteRequestFocus.value) {
        if (isExecuteRequestFocus.value) {
            Logger.d("4444 попытка поместить фокус на номер=" + isExecuteRequestFocus.value)

            flatNumberFocusRequester.requestFocus()

            Logger.d("4444 какой сейчас фокус на адресе =" + isFocusedAddress)

        }
    }

    LaunchedEffect(isApprovedData.value) {
        when(isApprovedData.value) {
            CheckData.APPROVED -> {
                val addressId = addresses[positionDropList].addr_id
                val oper = addresses[positionDropList].oper
                val address = addressText
                val flat = flatNumberText

                viewModel.getAddressById(
                    addressId = addressId,
                    oper = oper,
                    address = address,
                    flat = flat
                )
            }
            CheckData.NOT_APPROVED -> {
                // снак бар мол попробуйте выполнить запрос еще раз
            }
            CheckData.DEFAULT -> { }
        }
    }

    LaunchedEffect(addAddressResponse) {
        when(fromScreen) {
            ScreenRoute.DomofonScreen.route -> {
                isDvrOrDomofonAvailable.value = addAddressResponse?.data?.domofon ?: false
            }
            ScreenRoute.OutdoorScreen.route -> {
                isDvrOrDomofonAvailable.value = addAddressResponse?.data?.dvr ?: false
            }
            ScreenRoute.ProfileScreen.route -> {
                isDvrOrDomofonAvailable.value = true
            }
        }

        val addressString = getAddressString(addAddressResponse)
        if (isDvrOrDomofonAvailable.value) {

            val res = addAddressResponse?.data

         //   navHostController.navigate()
//            openAttachDocScreen(address = addressString, addedAddress = addedAddress?.data, navigationFrom = args.from)
        } else {
          //  navHostController.navigate()
//            openDomofonRequestScreen(address = addressString, addedAddress = addedAddress?.data, navigationFrom = args.from)
        }
    }


    DisposableEffect(Unit) {
        Logger.d("4444 DisposableEffect сработал фокус на адресе")
        addressFocusRequester.requestFocus()
        onDispose {}
    }

    class ManualVisualTransformation(val enableCursorMove: Boolean) : VisualTransformation {
        override fun filter(text: AnnotatedString): TransformedText {
            // ваша текущая логика фильтрации текста
            val offsetTranslator = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (enableCursorMove) offset else addressText.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (enableCursorMove) addressText.length else offset
                }
            }
            return TransformedText(AnnotatedString(addressText), offsetTranslator)
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    expandedAddresses = false
                }
            )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
//                        .onGloballyPositioned { coordinates ->
//                            textFieldSize = coordinates.size.toSize()
//                        }
                        .focusRequester(addressFocusRequester)
                        .onFocusChanged { focusState ->
                            isFocusedAddress = focusState.isFocused
                        },
                    value = addressText,
                    onValueChange = {
                        addressText = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = ManualVisualTransformation(enableCursorMove = true),
                    singleLine = true,
                    trailingIcon = {
                        if (addressText.isNotEmpty()) {
                            IconButton(onClick = {
                                addressText = ""
                            }) {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    imageVector = Icons.Rounded.Clear,
                                    contentDescription = "arrow",
                                    tint = Color.Black
                                )
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Начните вводить улицу...",
                            color = Color.Gray
                        )
                    }
                )
            }


            if (addresses.isEmpty() && addressText.length > 3) {
                InvalidInput()
            }

            if (errorNetwork) {
                ProgressBarErrorNetwork()
            }


            if (isShowTextFieldAndButton.value) {
                Logger.d("4444 открылись форма для номер и кнопка")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onGloballyPositioned { coordinates ->
                                // textFieldSize = coordinates.size.toSize()
                            }
                            .focusRequester(flatNumberFocusRequester)
                            .onFocusChanged { focusState ->
                                isFocusedFlatNumber = focusState.isFocused
                            },
                        value = flatNumberText,
                        onValueChange = {
                            flatNumberText = it
//                        expanded = true
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (flatNumberText.isNotEmpty()) {
                                IconButton(onClick = {
                                    flatNumberText = ""
                                }) {
                                    Icon(
                                        modifier = Modifier.size(24.dp),
                                        imageVector = Icons.Rounded.Clear,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Номер квартиры",
                                color = Color.Gray
                            )
                        }
                    )
                }

                val color = if (addressText.isNotEmpty() && flatNumberText.isNotEmpty()) {
                    ColorCustomResources.colorBazaMainBlue
                } else {
                    Color.LightGray
                }
                val isEnabled = addressText.isNotEmpty() && flatNumberText.isNotEmpty()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ElevatedButton(
                        modifier = Modifier
                            // .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            isApprovedData.value = checkData(
                                addressText = addressText,
                                flatNumberText = flatNumberText,
                                positionDropList = positionDropList
                            )
                        },
                        enabled = isEnabled,
                        content = {
                            Text(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp),
                                text = "Проверить доступные услуги"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = color
                        )
                    )
                }
                isExecuteRequestFocus.value = true
                Logger.d("4444 инит свойства для фокуса на номер=" + isExecuteRequestFocus.value)
            }

            if (expandedAddresses) {
                isClickAddressTextField.value = false
                Logger.d("4444 открылся выпадающий список")
                AnimatedVisibility(visible = expandedAddresses) {
                    Card(
                        modifier = Modifier
                            .padding(top = 16.dp),
//                        .width(textFieldSize.width.dp)
                        elevation = CardDefaults.cardElevation(),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color.Gray),
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color.White)
                                .padding(top = 16.dp, bottom = 16.dp),
                        ) {
                            if (addresses.isNotEmpty()) {
                                itemsIndexed(addresses) { index, address ->
                                    CategoryItems(
                                        title = address.toString(), // Здесь вы можете использовать нужное поле адреса
                                        onSelect = {
                                            scope.launch {
                                                addressText = ""
                                                delay(100L)
                                                addressText = it
                                                positionDropList = index
                                                // expandedAddresses = false
                                                // Logger.d("4444 попытка закрыть выпадающий список")
                                                Logger.d("4444 onSelect =" + it)
                                                selectedItemLength.value = it.length
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelect(title)
            }
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}


@Composable
fun InvalidInput() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Некорректный ввод",
            color = Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProgressBarErrorNetwork() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(55.dp),
            color = ColorCustomResources.colorBazaMainBlue,
            strokeWidth = 4.dp,
            trackColor = Color.White,
            strokeCap = StrokeCap.Square
        )
    }
}

private fun checkData(
    addressText: String,
    flatNumberText: String,
    positionDropList: Int
): CheckData {
    return if (addressText.matches(REGEX_ADDRESS)
        && flatNumberText.matches(REGEX_APT)
        && positionDropList != -1) {
        CheckData.APPROVED
    } else {
        CheckData.NOT_APPROVED
    }
}

private fun getAddressString(address: AddAddressResponse?): String {
    var addressString = ""
    address?.let {
        addressString = StringBuilder()
            //.append("г. ")
            .append(it.data.city)
            .append(", ")
            .append(it.data.street)
            .append(", ")
            .append(it.data.home)
            .append(" - ")
            .append(it.data.flat)
            .toString()
    }
    return addressString
}
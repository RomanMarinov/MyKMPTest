package presentation.ui.call_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.auth.AuthLoginBody
import domain.repository.PublicInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
//import moe.tlaster.precompose.viewmodel.ViewModel
//import moe.tlaster.precompose.viewmodel.viewModelScope

class CallActivityViewModel(
    //private val authRepository: AuthRepository,
    private val publicInfoRepository: PublicInfoRepository
) : ViewModel() {

    private var _supportCallNumber: MutableStateFlow<String> = MutableStateFlow("")
    val supportCallNumber: StateFlow<String> = _supportCallNumber

    private var _logInStatusCode: MutableStateFlow<Int?> = MutableStateFlow(null)
    val logInStatusCode: StateFlow<Int?> = _logInStatusCode

    init {
        getSupportCallNumber()
    }

    private fun getSupportCallNumber() {
        viewModelScope.launch(Dispatchers.IO) {
            val publicInfo = publicInfoRepository.getPublicInfo()
            _supportCallNumber.value = publicInfo.validateCallNumber
        }
    }

    fun login(authLoginBody: AuthLoginBody) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val httpResponse: HttpResponse? = authRepository.logIn(authLoginBody = authLoginBody)
//            when (httpResponse?.status?.value) {
//                200 -> { // переход на экран home
//                    _logInStatusCode.value = 200
//                }
//
//                404 -> { // С указанного номера не было звонка"
//                    _logInStatusCode.value = 404
//                }
//
//                else -> { // не правильно введен номер телефона
//                    _logInStatusCode.value = 0
//                }
//            }
//        }
    }
}
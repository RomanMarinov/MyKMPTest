package presentation.ui.call_activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import domain.model.auth.AuthLoginBody
import domain.repository.AuthRepository
import domain.repository.PublicInfoRepository
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CallActivityViewModel(
    private val authRepository: AuthRepository,
    private val publicInfoRepository: PublicInfoRepository
) : ViewModel() {

    private var _supportCallNumber: MutableStateFlow<String> = MutableStateFlow("")
    val supportCallNumber: StateFlow<String> = _supportCallNumber

    private var _logInStatusCode: MutableStateFlow<Int> = MutableStateFlow(-1)
    val logInStatusCode: StateFlow<Int> = _logInStatusCode

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
        viewModelScope.launch(Dispatchers.IO) {
            _logInStatusCode.value = -1 // установить по умолчанию для повторных запросов
            Logger.d{"4444 ViewPagerAuth authLoginBody=" + authLoginBody}

            val httpResponse: HttpResponse? = authRepository.logIn(authLoginBody = authLoginBody)

            Logger.d{"4444 ViewPagerAuth login=" + httpResponse?.status?.value}
            when (httpResponse?.status?.value) {

                200 -> { // переход на экран home
                    _logInStatusCode.value = 200
                }

                404 -> { // С указанного номера не было звонка"
                    _logInStatusCode.value = 404
                }

                else -> { // не правильно введен номер телефона
                    _logInStatusCode.value = 0
                }
            }
        }
    }

    fun resetIntervalCounters() {
        authRepository.resetIntervalCounters(true)
    }
}
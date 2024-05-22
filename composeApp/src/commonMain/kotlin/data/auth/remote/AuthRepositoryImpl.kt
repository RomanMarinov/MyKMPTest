package data.auth.remote

import co.touchlab.kermit.Logger
import data.auth.local.AppPreferencesRepository
import data.auth.remote.dto.AuthLoginBodyDTO
import data.auth.remote.dto.AuthLoginResponseDTO
import domain.model.auth.AuthLoginBody
import domain.repository.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay

// Задаём прогрессивный интервал для отправки запросов авторизации в секундах
// (каждый последующий интервал увеличивается на 1 секунду)
private var START_REQUEST_INTERVAL = 0L
private var CURRENT_REQUEST_INTERVAL = 0L
private const val INCREASE_REQUEST_INTERVAL = 1000L
private var isNeededToResetIntervalCounters = false

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val appPreferencesRepository: AppPreferencesRepository
) : AuthRepository {

    override fun resetIntervalCounters(resetCounters: Boolean) {
        if (resetCounters) {
            isNeededToResetIntervalCounters = true
        }
    }

    override suspend fun logIn(authLoginBody: AuthLoginBody): HttpResponse? {
//        logManager.writeLogToDB("Отправляем запрос на авторизацию с номером телефона '${body.phone}'")

        val httpResponse: HttpResponse? = null
        try {
            while (true) {

                if (isNeededToResetIntervalCounters) {
                    START_REQUEST_INTERVAL = 0L
                    CURRENT_REQUEST_INTERVAL = 0L
                    isNeededToResetIntervalCounters = false
                }

                START_REQUEST_INTERVAL += CURRENT_REQUEST_INTERVAL

                delay(CURRENT_REQUEST_INTERVAL)

                val authLoginBodyDTO = AuthLoginBodyDTO(
                    phone = authLoginBody.phone,
                    fingerprint = authLoginBody.fingerprint
                )
                val response: HttpResponse = httpClient.post("auth/login") {
                    contentType(ContentType.Application.Json)
                    setBody(body = authLoginBodyDTO)
                }
//
                Logger.d{"4444 ViewPagerAuth response=" + httpResponse?.status?.value}

                CURRENT_REQUEST_INTERVAL += INCREASE_REQUEST_INTERVAL

                when (response.status.value) {
                    200 -> {
                        if (!response.status.isSuccess()) throw Exception("200 Response is not successful : AuthRepositoryImpl/login()")
                        //logManager.writeLogToDB("Успешная авторизация с номером телефона '${body.phone}'")
                        Logger.d{"AUTH Успешная авторизация: AuthRepositoryImpl/login()"}
                        val responseBody = response.body<AuthLoginResponseDTO>()
                        val accessToken = responseBody.accessToken
                        val refreshToken = responseBody.refreshToken

                        appPreferencesRepository.setAuthToPrefsAndAuthState(
                            phone = authLoginBody.phone,
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                            fingerprint = authLoginBody.fingerprint
                        )
                        return response
                    }

                    404 -> {
                        //logManager.writeLogToDB("Ошибка авторизации с номером телефона '${body.phone}'")
                        Logger.d {"AUTH Не удалось авторизоваться: RepositoryImpl/login()"}
                        // Если вернулись на этот экран после набора номера
                        // и время ожидания 200-го ответа превысило 30 секунд,
                        // значит, ошибка связана с несоответствием номеров
                        // (или слишком долго думали)
                        if (CURRENT_REQUEST_INTERVAL > 7000L) {



                            //appAuth.removeAuthFromPrefsAndAuthState() // Галино
                            return response
                        }
                                  continue
                    }

                    else -> {
//                logManager.writeLogToDB("Ошибка авторизации с номером телефона '${body.phone}'")
                        //              appAuth.removeAuthFromPrefsAndAuthState()
                                    continue
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //logManager.writeLogToDB("Ошибка авторизации с номером телефона '${body.phone}'")
            //logManager.writeLogToDB(e.stackTraceToString())
            Logger.d{"4444 try catch AuthRepositoryImpl e=" + e}
            return httpResponse
        }
    }

//    override suspend fun logOut(body: FingerprintBody): Response<AuthLogoutResponse>? {
//        TODO("Not yet implemented")
//    }
//
//    override fun refreshTokenSync(body: AuthRefreshBody): Response<AuthLoginResponse>? {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun ipAuthorization(body: FingerprintBody): Response<AuthLoginResponse>? {
//        TODO("Not yet implemented")
//    }
}
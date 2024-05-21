package domain.repository

import domain.model.auth.AuthLoginBody
import io.ktor.client.statement.HttpResponse

interface AuthRepository {

    // auth
    fun resetIntervalCounters(resetCounters: Boolean)
    suspend fun logIn(authLoginBody: AuthLoginBody): HttpResponse?






//    suspend fun logOut(body: FingerprintBody): Response<AuthLogoutResponse>?
//    fun refreshTokenSync(body: AuthRefreshBody): Response<AuthLoginResponse>?
//    suspend fun ipAuthorization(body: FingerprintBody): Response<AuthLoginResponse>?
}
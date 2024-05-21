package data.auth.remote.dto

import domain.model.auth.AuthLoginResponse
import domain.model.auth.PayloadLogin
import kotlinx.serialization.Serializable

@Serializable
data class AuthLoginResponseDTO(
    val accessToken: String,
    val refreshToken: String,
    val payload: PayloadLoginDTO
) {
    fun mapToDomain() : AuthLoginResponse {
        return AuthLoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            payload = payload.mapToDomain()
        )
    }
}

@Serializable
data class PayloadLoginDTO(
    val phone: Long
) {
    fun mapToDomain() : PayloadLogin {
        return PayloadLogin(
            phone = phone
        )
    }
}
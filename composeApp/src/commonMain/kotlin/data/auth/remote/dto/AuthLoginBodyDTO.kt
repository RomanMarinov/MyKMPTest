package data.auth.remote.dto

import domain.model.auth.AuthLoginBody

data class AuthLoginBodyDTO(
    val phone: Long,
    val fingerprint: String
) {
    fun mapToDomain() : AuthLoginBody {
        return AuthLoginBody(
            phone = phone,
            fingerprint = fingerprint
        )
    }
}

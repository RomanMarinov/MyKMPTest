package domain.model.auth

data class AuthLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val payload: PayloadLogin
)

data class PayloadLogin(
    val phone: Long
)


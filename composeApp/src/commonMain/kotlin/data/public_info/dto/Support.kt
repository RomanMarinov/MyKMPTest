package data.public_info.dto

data class Support(
    val email: String,
    val messengers: Messengers,
    val phoneContact: PhoneContact,
    val worktime: List<String>
)
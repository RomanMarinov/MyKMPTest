package data.public_info.dto

data class MobileTariff(
    val gbytes: Int,
    val minutes: Int,
    val name: String,
    val price: Int,
    val sms: Int
)
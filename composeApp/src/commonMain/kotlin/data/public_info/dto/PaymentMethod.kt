package data.public_info.dto

data class PaymentMethod(
    val gateway: String,
    val subtitle: String,
    val svg_icon: String,
    val title: String
)
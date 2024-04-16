package data.public_info.dto

data class MarkerOutdoor(
    val additional: AdditionalOutdoor,
    val angle: Int,
    val latitude: Double,
    val longitude: Double,
    val title: String
)
package data.public_info.dto

data class AdditionalMap(
    val albumId: Int,
    val cameraName: String,
    val dtpCounts: Int,
    val location: String,
    val locationTitle: String,
    val previewUrl: String,
    val server: String,
    val token: String,
    val videoUrl: String
)
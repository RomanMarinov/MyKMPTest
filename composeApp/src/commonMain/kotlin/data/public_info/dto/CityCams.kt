package data.public_info.dto

data class CityCams(
    val count: Int,
    val markerCityCams: List<MarkerCityCam>,
    val title: String
)
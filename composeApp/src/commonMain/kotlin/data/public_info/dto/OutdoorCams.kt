package data.public_info.dto

data class OutdoorCams(
    val count: Int,
    val markers: List<MarkerOutdoor>,
    val title: String
)
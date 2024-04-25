package data.domofon.remote.model

data class Sputnik(
    val title: String,
    val addrId: Int,
    val deviceID: String,
    val fullControl: Boolean,
    val flat: Int,
    val oper: String,
    val porch: Int,
    val previewUrl: String,
    val videoUrl: String,
)

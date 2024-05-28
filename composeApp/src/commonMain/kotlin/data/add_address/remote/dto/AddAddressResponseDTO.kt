package data.add_address.remote.dto

data class AddAddressResponseDTO(
    val data: DataDTO
)

data class DataDTO(
    val id: Int,
    val addrId: Int,
    val city: String,
    val flat: String,
    val home: String,
    val oper: String,
    val street: String,
    val verificationStatus: String,
    val inet: Boolean,
    val ktv: Boolean,
    val domofon: Boolean,
    val dvr: Boolean
)
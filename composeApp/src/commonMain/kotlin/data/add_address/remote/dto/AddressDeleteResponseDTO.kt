package data.add_address.remote.dto

import domain.add_address.AddressDeleteResponse
import domain.add_address.DataDelete

data class AddressDeleteResponseDTO(
    val data: DataDeleteDTO
) {
    fun mapToDomain() : AddressDeleteResponse {
        return AddressDeleteResponse(
            data = data.mapToDomain()
        )
    }
}

data class DataDeleteDTO(
    val result: Boolean
) {
    fun mapToDomain() : DataDelete {
        return DataDelete(
            result = result
        )
    }
}

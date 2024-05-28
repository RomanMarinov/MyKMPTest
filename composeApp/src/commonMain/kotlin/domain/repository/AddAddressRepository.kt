package domain.repository

import domain.add_address.AddAddressBody
import domain.add_address.CheckAddressBody
import io.ktor.client.statement.HttpResponse

interface AddAddressRepository {
    suspend fun checkAddress(checkAddressBody: CheckAddressBody): HttpResponse?
    suspend fun getAddressById(addAddressBody: AddAddressBody): HttpResponse?
    suspend fun deleteAddress(id: Int): Boolean?
}
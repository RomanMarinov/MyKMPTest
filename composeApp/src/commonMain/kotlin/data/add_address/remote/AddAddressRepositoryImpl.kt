package data.add_address.remote

import data.add_address.remote.dto.AddAddressBodyDTO
import data.add_address.remote.dto.AddressDeleteResponseDTO
import domain.add_address.AddAddressBody
import domain.add_address.CheckAddressBody
import domain.repository.AddAddressRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AddAddressRepositoryImpl(
    private val httpClient: HttpClient
) : AddAddressRepository {

    override suspend fun checkAddress(checkAddressBody: CheckAddressBody): HttpResponse? {
        val checkAddressBodyDTO = checkAddressBody.mapToData()

        val response = httpClient.post("public/check_address") {
            contentType(ContentType.Application.Json)
            setBody(body = checkAddressBodyDTO)
        }
        return response
    }

    override suspend fun getAddressById(addAddressBody: AddAddressBody): HttpResponse? {
        val addAddressBodyDTO = AddAddressBodyDTO(
            addrId = addAddressBody.addrId,
            oper = addAddressBody.oper,
            address = addAddressBody.address,
            flat = addAddressBody.flat
        )
        val id = addAddressBody.addrId

       return try {
            val response = httpClient.post("user/address/{$id}") {
                contentType(ContentType.Application.Json)
                setBody(body = addAddressBodyDTO)
            }
           if (response.status.isSuccess()) {
//               logManager.writeLogToDB("Успешно получили инфо по адресу c addrId = '${body.addrId}'")
           } else {
//               logManager.writeLogToDB("Ошибка получения инфо по адресу c addrId = '${body.addrId}'")
           }
           return response
        } catch (e: Exception) {
           e.printStackTrace()
           //logManager.writeLogToDB("Ошибка получения инфо по адресу c addrId = '${body.addrId}': ${e.javaClass.simpleName}")
           //logManager.writeLogToDB(e.stackTraceToString())
           null
        }
    }


    override suspend fun deleteAddress(id: Int): Boolean {
        val response = httpClient.post("user/address/{$id}") {
            contentType(ContentType.Application.Json)
        }
        return if (response.status.isSuccess()) {
//            logManager.writeLogToDB("Успешно удалили адрес с id = $id")
            val result = response.body<AddressDeleteResponseDTO>().mapToDomain()
            result.data.result ?: true
        } else {
            //logManager.writeLogToDB("Ошибка удаления адреса с id = $id")
            false
        }
    }

//    override suspend fun getAddressById(
//        body: AddAddressBody
//    ): Response<AddAddressResponse>? {
//        logManager.writeLogToDB("Отправляем запрос на получение инфо по адресу c addrId = '${body.addrId}'")
//        Log.i("CHECK_ADDRESS", "Отправляем запрос на получение инфо по адресу c addrId = '${body.addrId}': DomofonRepositoryImpl/getAddressById()")
//        return try {
//            apiService.getAddressById(body, body.addrId).also {
//                if (it.isSuccessful) {
//                    logManager.writeLogToDB("Успешно получили инфо по адресу c addrId = '${body.addrId}'")
//                } else {
//                    logManager.writeLogToDB("Ошибка получения инфо по адресу c addrId = '${body.addrId}'")
//                }
//                return it
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            logManager.writeLogToDB("Ошибка получения инфо по адресу c addrId = '${body.addrId}': ${e.javaClass.simpleName}")
//            logManager.writeLogToDB(e.stackTraceToString())
//            null
//        }
//    }
}
package presentation.ui.add_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import data.add_address.remote.dto.CheckAddressResponseDTO
import domain.add_address.AddAddressBody
import domain.add_address.AddAddressResponse
import domain.add_address.CheckAddressBody
import domain.add_address.CheckAddressData
import domain.repository.AddAddressRepository
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddAddressViewModel(
    private val addAddressRepository: AddAddressRepository
) : ViewModel() {

    private var _addresses: MutableStateFlow<List<CheckAddressData>> = MutableStateFlow(emptyList())
    val addresses: StateFlow<List<CheckAddressData>> = _addresses

    private var _errorNetwork: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorNetwork: StateFlow<Boolean> = _errorNetwork

    private var _addAddressResponse: MutableStateFlow<AddAddressResponse?> = MutableStateFlow(null)
    val addAddressResponse: StateFlow<AddAddressResponse?> = _addAddressResponse

    fun checkAddress(addressText: String) {
        // список из viewModel.addresses приходит нормально на 3 символ
        val checkAddressBody = CheckAddressBody(text = addressText)
        viewModelScope.launch(Dispatchers.IO) {
            val response = addAddressRepository.checkAddress(checkAddressBody)

            Logger.d("4444 address=" + response)

            response?.let {
                if (it.status.isSuccess()) {
                    val result = it.body<CheckAddressResponseDTO>()
                    result.mapToDomain().data?.let { listAddress ->
                        _addresses.value = listAddress
                    }
                } else {
                    _errorNetwork.value = true
                }
            }
        }
    }

    fun getAddressById(
        addressId: Int,
        oper: String,
        address: String,
        flat: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = addAddressRepository.getAddressById(
                AddAddressBody(
                    addrId = addressId,
                    oper = oper,
                    address = address,
                    flat = flat
                )
            )
            val result = response?.body<AddAddressResponse>()
            _addAddressResponse.value = result
        }
    }


//    suspend fun getAddressById(body: AddAddressBody): AddAddressResponse? {
//        return try {
//            outDoorRepository.getAddressById(body)?.body()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }
}
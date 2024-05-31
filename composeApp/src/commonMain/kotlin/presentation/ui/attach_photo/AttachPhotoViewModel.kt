package presentation.ui.attach_photo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.AddAddressRepository
import domain.repository.UserInfoRepository
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import presentation.ui.add_adress_result.model.ResultSendPhoto

class AttachPhotoViewModel(
    private val userInfoRepository: UserInfoRepository,
    private val addAddressRepository: AddAddressRepository
) : ViewModel() {

    private var _resultSendPhoto: MutableStateFlow<ResultSendPhoto> = MutableStateFlow(ResultSendPhoto.DEFAULT)
    val resultSendPhoto: StateFlow<ResultSendPhoto> = _resultSendPhoto

    private var isNeedAssociatePhoneToServices: MutableState<Boolean?> = mutableStateOf(null)

    init {
        checkPhoneLinkedToAccount()
    }

    private fun checkPhoneLinkedToAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userInfoRepository.getUserInfo().data.profile.needAssociatePhoneToServices
            isNeedAssociatePhoneToServices.value = result
        }
    }

    fun sendPhoto(imageByteArray: ByteArray?, addrId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            imageByteArray?.let { image ->
               val response = addAddressRepository.uploadImage(imageByteArray = image, id = addrId)
                response?.let {
                    if (it.status.isSuccess()) {
                        _resultSendPhoto.value = ResultSendPhoto.SUCCESS
                    } else {
                        _resultSendPhoto.value = ResultSendPhoto.FAILURE
                    }
                }
            }
        }
    }

    fun checkPhoneLinkedAndSendPhoto() {
        isNeedAssociatePhoneToServices.value?.let {
            if (it) {
                // отправить на ui
                // Snackbar.make(
//                binding.root,
//                "Чтобы добавить адрес необходимо привязать номер телефона к аккаунту",
//                Snackbar.LENGTH_INDEFINITE
//            )
            } else {
//                val galleryImgPath = getPath(requireActivity(), imageUri.value) ?: ""
//                val cameraImgPath = Constants.absolutePath
//
//                val finalPath = if (galleryImgPath.matches(GALLERY_PATH_REGEX)) galleryImgPath
//                else if (cameraImgPath.matches(CAMERA_PATH_REGEX)) cameraImgPath
//                else {
//                    viewModel.writeLogs("Источник изображения не найден")
//                    Snackbar.make(
//                        binding.root,
//                        "Источник изображения не найден.\nПопробуйте прикрепить фото\nс камеры или из Галереи",
//                        Snackbar.LENGTH_INDEFINITE
//                    )
//                        .setAction(R.string.ok) {}
//                        .setTextMaxLines(3)
//                        .setTextColor(resources.getColor(R.color.white, null))
//                        .show()
//                    ""
//                }


            }
        }


    }


//    private fun getPath(context: Context, uri: Uri?): String? {
//        var result: String? = null
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val cursor: Cursor? =
//            uri?.let { context.contentResolver.query(it, proj, null, null, null) }
//        try {
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
//                    result = cursor.getString(column_index)
//                }
//                cursor.close()
//            }
//        } catch (e: IllegalArgumentException) {
//            println("Ошибка: пытаемся создать путь к картинке из галереи, которая не выбрана")
//            e.printStackTrace()
//            return null
//        }
//
//        if (result == null) {
//            result = "Not found"
//        }
//        return result
//    }
}


//    binding.sendPhoto.setOnClickListener {
//        viewModel.writeLogs("Нажали кнопку 'Отправить на проверку'")
//        binding.progressBar.visibility = View.VISIBLE
//        if (viewModel.isNeedAssociatePhoneToServices) {
//            viewModel.writeLogs("Номер телефона не привязан, отмена")
//            Snackbar.make(
//                binding.root,
//                "Чтобы добавить адрес необходимо привязать номер телефона к аккаунту",
//                Snackbar.LENGTH_INDEFINITE
//            )
//                .setTextColor(resources.getColor(R.color.white, null))
//                .setAction(R.string.ok) {}
//                .show()
//        } else {
//            viewModel.writeLogs("Номер телефона привязан, все ок")
//            val galleryImgPath = getPath(requireActivity(), imageUri.value) ?: ""
//            val cameraImgPath = Constants.absolutePath
//
//            val finalPath = if (galleryImgPath.matches(GALLERY_PATH_REGEX)) galleryImgPath
//            else if (cameraImgPath.matches(CAMERA_PATH_REGEX)) cameraImgPath
//            else {
//                viewModel.writeLogs("Источник изображения не найден")
//                Snackbar.make(
//                    binding.root,
//                    "Источник изображения не найден.\nПопробуйте прикрепить фото\nс камеры или из Галереи",
//                    Snackbar.LENGTH_INDEFINITE
//                )
//                    .setAction(R.string.ok) {}
//                    .setTextMaxLines(3)
//                    .setTextColor(resources.getColor(R.color.white, null))
//                    .show()
//                ""
//            }
//
//            if (imageUri.value == null) {
//                viewModel.writeLogs("Фото не прикреплено") //// ТУУУУУТ
//                Toast.makeText(requireActivity(), "Фото не прикреплено", Toast.LENGTH_LONG)
//                    .show()
//            }
//
//            val requestBody = File(finalPath).asRequestBody("image/*".toMediaTypeOrNull())
//
//            val body = getMultipartBody(requestBody, File(finalPath))
//
//            addressArg.addressData?.let { addrId = it.id }
//
//            lifecycleScope.launch(Dispatchers.IO) {
//                try {
//                    body.let {
//                        val isSentSuccessfully = viewModel.sendPhoto(
//                            id = addrId,
//                            image = it
//                        )
//
//                        Log.d("4444", " isSentSuccessfully=" + isSentSuccessfully)
//                        if (isSentSuccessfully) {
//                            viewModel.writeLog("Фото отправлено")
//
//                            withContext(Dispatchers.Main) {
//                                binding.progressBar.visibility = View.GONE
//                            }
//                            Log.d(
//                                "4444",
//                                " isSentSuccessfully addressArg.navigationFrom=" + addressArg.navigationFrom
//                            )
//                            when (addressArg.navigationFrom) {
//                                Constants.FromFragmentMarkers.FROM_DVR -> {
//                                    moveToRequestResultBottomSheet(requestType = Constants.FromFragmentMarkers.FROM_DVR_REQUEST_ATTACH_PHOTO)
//                                }
//
//                                Constants.FromFragmentMarkers.FROM_DOMOFON -> {
//                                    moveToRequestResultBottomSheet(requestType = Constants.FromFragmentMarkers.FROM_DOMOFON_REQUEST_ATTACH_PHOTO)
//                                }
//
//                                Constants.FromFragmentMarkers.FROM_PROFILE -> {
//                                    moveToRequestResultBottomSheet(requestType = Constants.FromFragmentMarkers.FROM_PROFILE)
//                                }
//                            }
////                                closeCurrentBottomSheet()
//                            // closePreviousBottomSheet()
//                        } else {
//                            viewModel.writeLog("Фото не отправлено")
//                            withContext(Dispatchers.Main) {
//                                binding.progressBar.visibility = View.GONE
//                                Toast.makeText(
//                                    requireActivity(),
//                                    "Не удалось отправить фото, попробуйте снова",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    viewModel.writeLog("Ошибка отправки фото: ${e.localizedMessage} ${e.javaClass.simpleName}")
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
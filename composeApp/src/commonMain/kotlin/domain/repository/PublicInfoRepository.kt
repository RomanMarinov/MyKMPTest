package domain.repository

import data.public_info.remote.dto.Data


interface PublicInfoRepository {
    suspend fun getPublicInfo() : Data
}
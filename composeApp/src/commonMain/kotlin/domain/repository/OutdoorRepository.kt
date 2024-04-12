package domain.repository

import domain.model.Dvr

interface OutdoorRepository {
    suspend fun getOutdoors(): List<Dvr>
}
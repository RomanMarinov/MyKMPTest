package domain.repository

import data.domofon.remote.model.Sputnik

interface DomofonRepository {
    suspend fun getDomofon() : List<Sputnik>
}
package domain.repository

import domain.model.domofon.SputnikSelected

interface DomofonRepository {
    suspend fun getDomofon() : List<SputnikSelected>
}
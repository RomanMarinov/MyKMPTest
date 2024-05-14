package data.domofon.remote

import data.domofon.remote.model.Sputnik
import domain.repository.DomofonRepository
import io.ktor.client.HttpClient

class DomofonRepositoryImpl(
    private val httpClient: HttpClient,
) : DomofonRepository {

    override suspend fun getDomofon() = getFakeSputnik()

    private fun getFakeSputnik(): List<Sputnik> {
        return listOf(
            Sputnik(
                title = "Тестовая (ул.) 1, 2 подъезд",
                addrId = 980,
                deviceID = "e344f4bb-fd97-4f7d-b12c-2aab350abcdd",
                fullControl = true,
                flat = 2,
                oper = "baza",
                porch = 2,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/e344f4bb-fd97-4f7d-b12c-2aab350abcdd?token=MjkwYTFkNjY5ODIxMjJhYzI2MWIyZjQyMmFkZjQ0YWFhN2MzOGFiNS4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/e344f4bb-fd97-4f7d-b12c-2aab350abcdd/embed.html?dvr=true&token=MjkwYTFkNjY5ODIxMjJhYzI2MWIyZjQyMmFkZjQ0YWFhN2MzOGFiNS4xNzE2MjE1OTM1",
                isSelected = false
            ),
            Sputnik(
                title = "Тестовая(ул.)1, 3 подъезд",
                addrId = 980,
                deviceID = "a8afbbde-981b-492f-8b4c-e1af5edd5b2b",
                fullControl = true,
                flat = 2,
                oper = "baza",
                porch = 3,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/a8afbbde-981b-492f-8b4c-e1af5edd5b2b?token=YjA0NTFiNzU3OTFmMWM0YjRhODkzODM5NjY5OGIzZTJjOThkMmZkNi4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/a8afbbde-981b-492f-8b4c-e1af5edd5b2b/embed.html?dvr=true&token=YjA0NTFiNzU3OTFmMWM0YjRhODkzODM5NjY5OGIzZTJjOThkMmZkNi4xNzE2MjE1OTM1",
                isSelected = true
            ),
            Sputnik(
                title = "Ярославская (ул.) 42, 5 подъезд",
                addrId = 20290,
                deviceID = "db275fc8-b2ea-48a8-a68e-44c4fecfe689",
                fullControl = true,
                flat = 241,
                oper = "baza",
                porch = 5,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/db275fc8-b2ea-48a8-a68e-44c4fecfe689?token=YmE4M2RmYjExOTAwOTVjMDIyNWM2YTI4Y2ExYjgyN2MwN2RlMjYyOC4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/db275fc8-b2ea-48a8-a68e-44c4fecfe689/embed.html?dvr=true&token=YmE4M2RmYjExOTAwOTVjMDIyNWM2YTI4Y2ExYjgyN2MwN2RlMjYyOC4xNzE2MjE1OTM1",
                isSelected = true
            ),
            Sputnik(
                title = "Ярославская (ул.) 42, 1 подъезд",
                addrId = 20290,
                deviceID = "0ff068a2-38cc-47cd-acec-ce27cc9ec2a0",
                fullControl = false,
                flat = 241,
                oper = "baza",
                porch = 1,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/0ff068a2-38cc-47cd-acec-ce27cc9ec2a0?token=OGYwZjYxYzg2OWQzMzQ1MzdjZGYwYzYzZWZmY2Q2OWI1NjA0MzcwMC4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/0ff068a2-38cc-47cd-acec-ce27cc9ec2a0/embed.html?dvr=true&token=OGYwZjYxYzg2OWQzMzQ1MzdjZGYwYzYzZWZmY2Q2OWI1NjA0MzcwMC4xNzE2MjE1OTM1",
                isSelected = false
            ),
            Sputnik(
                title = "Ярославская (ул.) 42, 2 подъезд",
                addrId = 20290,
                deviceID = "7905fec2-32bf-4584-b7ce-08f95e2434b5",
                fullControl = false,
                flat = 241,
                oper = "baza",
                porch = 2,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/7905fec2-32bf-4584-b7ce-08f95e2434b5?token=YzVhOWMyYTk2YjBiNDZkOTE0OTAzNmE3NmY3M2E1ZGQ3Y2VmNmEwZi4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/7905fec2-32bf-4584-b7ce-08f95e2434b5/embed.html?dvr=true&token=YzVhOWMyYTk2YjBiNDZkOTE0OTAzNmE3NmY3M2E1ZGQ3Y2VmNmEwZi4xNzE2MjE1OTM1",
                isSelected = false
            ),
            Sputnik(
                title = "Ярославская (ул.) 42, 3 подъезд",
                addrId = 20290,
                deviceID = "13ddce5f-71be-41da-9e6c-4726ce5cfdf3",
                fullControl = false,
                flat = 241,
                oper = "baza",
                porch = 3,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/13ddce5f-71be-41da-9e6c-4726ce5cfdf3?token=ZTgyYjcwMjE2NTc2OTc1ODA4OTNlZjBjOGRmMTdmMjZmN2I5MWJhYi4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/13ddce5f-71be-41da-9e6c-4726ce5cfdf3/embed.html?dvr=true&token=ZTgyYjcwMjE2NTc2OTc1ODA4OTNlZjBjOGRmMTdmMjZmN2I5MWJhYi4xNzE2MjE1OTM1",
                isSelected = false
            ),
            Sputnik(
                title = "Ярославская (ул.) 42, 4 подъезд",
                addrId = 20290,
                deviceID = "6acb8c95-28ff-4e7c-a33a-d48c11725756",
                fullControl = false,
                flat = 241,
                oper = "baza",
                porch = 4,
                previewUrl = "https://public-api-test.baza.net/domofon/preview/6acb8c95-28ff-4e7c-a33a-d48c11725756?token=YzFhZGExYzA5MGE2ZWJjYjM3OTUyMzMwZTI0MmY1MTg3ODlhYmEwOC4xNzE2MjE1OTM1",
                videoUrl = "https://sputnikdvr1.baza.net/6acb8c95-28ff-4e7c-a33a-d48c11725756/embed.html?dvr=true&token=YzFhZGExYzA5MGE2ZWJjYjM3OTUyMzMwZTI0MmY1MTg3ODlhYmEwOC4xNzE2MjE1OTM1",
                isSelected = false
            )
        )
    }
}




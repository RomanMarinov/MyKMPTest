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
                title = "Добролюбова (ул.) 26, Калитка",
                addrId = 2698,
                deviceID = "4f6e8f98-ea2a-40ef-a203-0730d825ac2d",
                fullControl = true,
                flat = 33,
                oper = "baza",
                porch = 0,
                previewUrl = "https://api.baza.net/domofon/preview/4f6e8f98-ea2a-40ef-a203-0730d825ac2d?token=NjYwOWNhYTlkODRlYWZiZjk5MzExNWFhNzVjNDc5ZmZmOWNjNjhhNC4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/4f6e8f98-ea2a-40ef-a203-0730d825ac2d/embed.html?dvr=true&token=NjYwOWNhYTlkODRlYWZiZjk5MzExNWFhNzVjNDc5ZmZmOWNjNjhhNC4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Добролюбова (ул.) 26, Калитка",
                addrId = 2698,
                deviceID = "8af0ab9f-431a-4bd4-aad4-8ba7c47c178c",
                fullControl = true,
                flat = 33,
                oper = "baza",
                porch = 0,
                previewUrl = "https://api.baza.net/domofon/preview/8af0ab9f-431a-4bd4-aad4-8ba7c47c178c?token=YzBlZmIxMmVhMzFlYTg2NGUyODc0NzE0ODA4YjM1YTBjM2YzZDczYi4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/8af0ab9f-431a-4bd4-aad4-8ba7c47c178c/embed.html?dvr=true&token=YzBlZmIxMmVhMzFlYTg2NGUyODc0NzE0ODA4YjM1YTBjM2YzZDczYi4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Возрождения (ул.) 74в, 1 подъезд",
                addrId = 2282,
                deviceID = "e7d96a52-b9ed-45ec-a457-11275f8db217",
                fullControl = true,
                flat = 26,
                oper = "baza",
                porch = 1,
                previewUrl = "https://api.baza.net/domofon/preview/e7d96a52-b9ed-45ec-a457-11275f8db217?token=Yzk3ZTdiY2NkZThkYWVlZjJiNWRhYzQwYmMwZDU5MzVlYTJiY2ZiNi4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/e7d96a52-b9ed-45ec-a457-11275f8db217/embed.html?dvr=true&token=Yzk3ZTdiY2NkZThkYWVlZjJiNWRhYzQwYmMwZDU5MzVlYTJiY2ZiNi4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Добролюбова (ул.) 26, 1 подъезд",
                addrId = 2698,
                deviceID = "2cc28d92-3969-44c6-8468-cbd2a5015715",
                fullControl = true,
                flat = 33,
                oper = "baza",
                porch = 1,
                previewUrl = "https://api.baza.net/domofon/preview/2cc28d92-3969-44c6-8468-cbd2a5015715?token=MGZjNjQwN2Y0Nzg1ZGFlZWQ3ZjIzNGI3MDQ5NWE1Yjc3MTk4NjRkNi4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/2cc28d92-3969-44c6-8468-cbd2a5015715/embed.html?dvr=true&token=MGZjNjQwN2Y0Nzg1ZGFlZWQ3ZjIzNGI3MDQ5NWE1Yjc3MTk4NjRkNi4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Бурмагиных (ул.) 34к2, 1 подъезд",
                addrId = 20770,
                deviceID = "030052ae-9d59-4564-b931-005ad0c5300b",
                fullControl = false,
                flat = 100,
                oper = "baza",
                porch = 1,
                previewUrl = "https://api.baza.net/domofon/preview/030052ae-9d59-4564-b931-005ad0c5300b?token=YmEyYjNmOTdmZjBkNWQ1NzMzMmQ4OTk4Yzc2ZmRhZTc5MDZhNWU2NC4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/030052ae-9d59-4564-b931-005ad0c5300b/embed.html?dvr=true&token=YmEyYjNmOTdmZjBkNWQ1NzMzMmQ4OTk4Yzc2ZmRhZTc5MDZhNWU2NC4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Добролюбова (ул.) 26, 2 подъезд",
                addrId = 2698,
                deviceID = "30b32e40-0fda-4b15-8710-597c7c71baa7",
                fullControl = false,
                flat = 33,
                oper = "baza",
                porch = 2,
                previewUrl = "https://api.baza.net/domofon/preview/30b32e40-0fda-4b15-8710-597c7c71baa7?token=ZTFlMDA2MzA5ZWQ1YmViMzc2YmM3NDFjZDFiZGE4OGIzYTlhZTFkNC4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/30b32e40-0fda-4b15-8710-597c7c71baa7/embed.html?dvr=true&token=ZTFlMDA2MzA5ZWQ1YmViMzc2YmM3NDFjZDFiZGE4OGIzYTlhZTFkNC4xNzE0NjQ4MjA5"
            ),

            Sputnik(
                title = "Огородный (пер.) 7, 2 подъезд",
                addrId = 667,
                deviceID = "35d2b6f7-380e-4dc6-b9b5-24c3ea69e632",
                fullControl = false,
                flat = 32,
                oper = "baza",
                porch = 2,
                previewUrl = "https://api.baza.net/domofon/preview/35d2b6f7-380e-4dc6-b9b5-24c3ea69e632?token=NTQ5ZmQ1NGY0MTU3NDM2ZDMwZjU4MDFjY2YzZWExNTRhNzZmYTUwOC4xNzE0NjQ4MjA5",
                videoUrl = "https://sputnikdvr1.baza.net/35d2b6f7-380e-4dc6-b9b5-24c3ea69e632/embed.html?dvr=true&token=NTQ5ZmQ1NGY0MTU3NDM2ZDMwZjU4MDFjY2YzZWExNTRhNzZmYTUwOC4xNzE0NjQ4MjA5"
            )
        )
    }
}
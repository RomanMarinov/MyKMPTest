package data.public_info.remote


import data.public_info.remote.dto.Data
import data.public_info.remote.dto.ResponsePublicInfo

import domain.repository.PublicInfoRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType

import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.http.append
import io.ktor.http.content.TextContent
import io.ktor.http.contentType

class PublicInfoRepositoryImpl(
    private val httpClient: HttpClient
) : PublicInfoRepository {
    override suspend fun getPublicInfo(): Data {



        val response: HttpResponse = httpClient.get("public/info")

        val res = response.body<ResponsePublicInfo>().data



        return res
    }
}
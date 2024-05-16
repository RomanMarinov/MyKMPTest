package di

//import io.ktor.client.engine.cio.CIO
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

//import org.koin.dsl.module

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    single<HttpClient> {
        HttpClient() {
            var converter: KotlinxSerializationConverter? = null

//            HttpClient(CIO) {

            // true говорит HttpClient о том, что вы ожидаете успешный ответ от сервера
            // по умолчанию, и он будет генерировать исключения в случае неудачного ответа.
            // expectSuccess = true

            install(ResponseObserver) {
                onResponse { response ->
                    //co.touchlab.kermit.Logger.d {" 4444 networkModule ResponseObserver HTTP onResponse= =$response.status.value" }
                }
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        //co.touchlab.kermit.Logger.d {" 4444 networkModule httpClient =$message" }
                    }
                }
                level = LogLevel.ALL
            }

//            /////
//            install(ContentNegotiation) {
//                converter = KotlinxSerializationConverter(Json {
//                    prettyPrint = true
//                    ignoreUnknownKeys = true
//                   // explicitNulls = false
//                })
//
//                register(ContentType.Application.Json, converter!!)
//            }
//
//            //////

            install(ContentNegotiation) {
                json(Json {
                    //explicitNulls = false
                    // при десериализации JSON данные не будут потеряны из-за наличия дополнительных полей
                    ignoreUnknownKeys = true
                    // Результирующий JSON будет содержать отступы, переносы строк и пробелы для улучшения его визуального восприятия.
                    prettyPrint = true
                    // игнорировать некоторые ошибки формата JSON и пытаться продолжить разбор, вместо выбрасывания исключения.
                    // isLenient = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 100000
                requestTimeoutMillis = 100000
                connectTimeoutMillis = 100000
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.baza.net"
                }
            }
        }
    }
}
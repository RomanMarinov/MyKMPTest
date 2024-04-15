//package di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

//abstract class KtorApi {
//
//    companion object {
//        const val BASE_URL = "https://api.baza.net"
//    }
//
//    val httpClient = HttpClient(CIO) {
//
//        // true говорит HttpClient о том, что вы ожидаете успешный ответ от сервера
//        // по умолчанию, и он будет генерировать исключения в случае неудачного ответа.
//       // expectSuccess = true
//
//        install(ResponseObserver) {
//            onResponse { response ->
//                //Log.d("4444", " ResponseObserver HTTP onResponse=" + response.status.value)
//            }
//        }
//        install(Logging) {
//            logger = object : Logger {
//                override fun log(message: String) {
//                    //Log.d("HTTP Client", message)
//                   // co.touchlab.kermit.Logger.d // https://www.youtube.com/watch?v=QztfvKcvIrs
//                    // 14.47
//                    println("HTTP Client $message")
//                }
//            }
//            level = LogLevel.ALL
//        }
//
//        install(ContentNegotiation) {
//            json(Json {
//                // при десериализации JSON данные не будут потеряны из-за наличия дополнительных полей
//                ignoreUnknownKeys = true
//                // Результирующий JSON будет содержать отступы, переносы строк и пробелы для улучшения его визуального восприятия.
//                prettyPrint = true
//                // игнорировать некоторые ошибки формата JSON и пытаться продолжить разбор, вместо выбрасывания исключения.
//               // isLenient = true
//            })
//        }
//
//        install(HttpTimeout) {
//            socketTimeoutMillis = 100000
//            requestTimeoutMillis = 100000
//            connectTimeoutMillis = 100000
//        }
//    }
//}
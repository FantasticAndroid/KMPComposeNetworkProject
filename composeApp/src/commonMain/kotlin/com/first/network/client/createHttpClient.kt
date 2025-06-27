package com.first.network.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "www.purgomalum.com"
const val CENSOR_URL = "service/json"
/**
 *
 * @param engine HttpClientEngine
 * @return HttpClient
 */
fun createHttpClient(engine: HttpClientEngine) : HttpClient = HttpClient(engine) {

    install(ContentNegotiation){
        json(json = Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging){
        level = LogLevel.ALL
        // To redirect logs to other logger lib only
        /*logger = object : Logger{
            override fun log(message: String) {
                println("CustomLogger: $message")
            }
        }*/
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
            //header("", "")
            //contentType(ContentType("application", "json"))
        }
    }

    // For Demo Only. How auth can be integrated.
    /*install(Auth){
        bearer {
            loadTokens {
                BearerTokens( accessToken = "DummyToken", refreshToken = "DummyRefreshToken")
            }
        }
    }*/
}
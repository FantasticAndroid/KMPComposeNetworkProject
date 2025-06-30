package com.first.network

import com.first.network.client.CENSOR_URL
import com.first.network.client.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

interface ICensorRepository {
    suspend fun getCensoredText(text: String) : Result<String>
}

class CensorRepository() : ICensorRepository {

    private val httpClient : HttpClient = createHttpClient(getNetworkEngine())

    override suspend fun getCensoredText(text: String) : Result<String> {

        val response = try {
            httpClient.get(urlString = CENSOR_URL){
                parameter("text", text)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.failure<String>(e)
        } catch(e: SerializationException) {
            return Result.failure<String>(e)
        } catch (e: Exception){
            return Result.failure<String>(e)
        }

        return when(response.status.value){
            in 200..299 -> {
                val resp = response.body<CensoredResponse>()
                if(!resp.result.isNullOrEmpty()){
                    Result.success(resp.result)
                }else if (!resp.error.isNullOrEmpty()){
                    Result.failure(Exception(resp.error))
                }else
                    Result.failure(NullPointerException())
            }
            401 -> Result.failure(Exception("UNAUTHORIZED"))
            409 -> Result.failure(Exception("CONFLICT"))
            408 -> Result.failure(Exception("REQUEST_TIMEOUT"))
            413 -> Result.failure(Exception("PAYLOAD_TOO_LARGE"))
            in 500..599 -> Result.failure(Exception("SERVER_ERROR"))
            else -> Result.failure(Exception("UNKNOWN_ERROR"))
        }
    }
}
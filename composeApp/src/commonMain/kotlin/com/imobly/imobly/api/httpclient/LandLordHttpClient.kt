package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.api.dto.EmailDTO
import com.imobly.imobly.domain.LandLord
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class LandLordHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/locadores"

    suspend fun getProfile(): LandLord {
        val response = httpClient.get("$baseUrl/encontrarperfil") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun updateProfile(landLord : LandLord): ResponseMessage {
        val response = httpClient.put("$baseUrl/atualizarperfil") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(landLord)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun sendCodeForUpdateEmail(dto : EmailDTO): ResponseMessage {
        val response = httpClient.patch("$baseUrl/enviarcodigoparaatualizaremail") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun updateEmail(code : String): ResponseMessage {
        val response = httpClient.patch("$baseUrl/atualizaremail/$code") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun deleteProfile(): ResponseMessage {
        val response = httpClient.delete("$baseUrl/deletarperfil") {
            header("Authorization", "Bearer $TOKEN")
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}
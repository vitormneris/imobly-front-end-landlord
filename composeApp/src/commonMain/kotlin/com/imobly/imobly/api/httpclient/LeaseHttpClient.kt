package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.LeaseDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.Lease
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class LeaseHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/locacoes"

    suspend fun searchAllByTitleOrName(titleOrName: String? = ""): List<Lease> {
        val response = httpClient.get("$baseUrl/encontrartodos?nomeoutitulo=$titleOrName") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun findById(id: String): Lease {
        val response = httpClient.get("$baseUrl/encontrarporid/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun create(lease : LeaseDTO): ResponseMessage {
        val response = httpClient.post("$baseUrl/inserir") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(lease)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun update(id: String, lease : Lease): ResponseMessage {
        val response = httpClient.patch("$baseUrl/atualizar/$id") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(lease)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun toggleEnable(id: String): ResponseMessage {
        val response = httpClient.patch("$baseUrl/alternarativo/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}
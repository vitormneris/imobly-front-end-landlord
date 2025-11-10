package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.LandLord
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class CategoryHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/categorias"

    suspend fun searchAll(): List<Category> =
        httpClient.get("$baseUrl/encontrartodos")
            .body()

    suspend fun create(category : Category): ResponseMessage {
        val response = httpClient.post("$baseUrl/inserir") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(category)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun update(id: String, category : Category): ResponseMessage {
        val response = httpClient.put("$baseUrl/atualizar/$id") {
            header("Authorization", "Bearer $TOKEN")
            contentType(ContentType.Application.Json)
            setBody(category)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun delete(id: String): ResponseMessage {
        val response = httpClient.delete("$baseUrl/deletar/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}
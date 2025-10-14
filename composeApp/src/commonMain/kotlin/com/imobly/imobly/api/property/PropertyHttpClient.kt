package com.imobly.imobly.api.property

import com.imobly.imobly.api.URL_BASE
import com.imobly.imobly.domain.Property
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class PropertyHttpClient(val httpClient: HttpClient) {

    suspend fun searchAll() : List<Property> =
        httpClient.get("${URL_BASE}/propriedades/encontrartodos")
            .body()

    suspend fun create(property : Property) {
        httpClient.post("${URL_BASE}/propriedades/inserir") {
            contentType(ContentType.Application.Json)
            setBody(property)
        }
    }

    suspend fun update(id: String, property : Property) {
        httpClient.put("${URL_BASE}/propriedades/atualizar/$id") {
            contentType(ContentType.Application.Json)
            setBody(property)
        }
    }
}
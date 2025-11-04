package com.imobly.imobly.api.category

import com.imobly.imobly.domain.Category
import com.imobly.imobly.domain.Property
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class CategoryHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/categorias"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun searchAll(): List<Category> =
        httpClient.get("$baseUrl/encontrartodos")
            .body()
}
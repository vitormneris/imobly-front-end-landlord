package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.domain.Payment
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class PaymentHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/pagamentos"

    suspend fun searchByLeaseId(id: String): Payment {
        val response = httpClient.get("$baseUrl/encontrarporlocacaoid/$id") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }
}
package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.domain.graph.RentByMonth
import com.imobly.imobly.domain.graph.RentedProperties
import com.imobly.imobly.domain.graph.RentsPaidThisMonth
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class GraphicHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/grafico"

    suspend fun getChartRentByMonth(): RentByMonth {
        val response = httpClient.get("$baseUrl/aluguelpormes") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun getChartRentedProperties(): RentedProperties {
        val response = httpClient.get("$baseUrl/propriedadesalugadas") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun getChartRentsPaidThisMonth(): RentsPaidThisMonth {
        val response = httpClient.get("$baseUrl/alugueispagosnestemes") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }
}
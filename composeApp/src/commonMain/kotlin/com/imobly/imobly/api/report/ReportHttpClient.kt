package com.imobly.imobly.api.report

import com.imobly.imobly.domain.Report
import com.imobly.imobly.domain.ReportStatus
import com.imobly.imobly.domain.ResponseMessage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

class ReportHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/reportacoes"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun searchAll(): List<Report> =
        httpClient.get("$baseUrl/encontrartodos")
            .body()

    suspend fun create(report: Report): ResponseMessage {

        val response = httpClient.post("$baseUrl/inserir") {
            setBody(json.encodeToString(report))
        }

        if (response.status.isSuccess()) {
            return ResponseMessage()
        }
        return response.body<ResponseMessage>()
    }

    suspend fun replyToReport(id: String, response: String): ResponseMessage {
        val response = httpClient.patch("$baseUrl/responderreportacao/$id") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("response" to response))
        }
        if (response.status.isSuccess()) {
            return ResponseMessage()
        }
        return response.body<ResponseMessage>()
    }

    suspend fun updateStatus(id: String, status: ReportStatus): ResponseMessage {
        val response = httpClient.patch("$baseUrl/atualizarstatus/$id") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("status" to status))
        }
        if (response.status.isSuccess()) {
            return ResponseMessage()
        }
        return response.body<ResponseMessage>()
    }

    suspend fun delete(id: String): Boolean {
        return httpClient.delete("$baseUrl/deletar/$id").status.isSuccess()
    }
}
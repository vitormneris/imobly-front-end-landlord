package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.api.dto.UpdateReportDTO
import com.imobly.imobly.domain.Report
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class ReportHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/reportacoes"

    suspend fun searchAllByTitleOrMessageOrName(titleOrMessageOrName: String? = ""): List<Report> {
        val response = httpClient.get("$baseUrl/encontrartodos?titulooumensagemounome=$titleOrMessageOrName") {
            header("Authorization", "Bearer $TOKEN")
        }
        return response.body()
    }

    suspend fun create(report: Report): ResponseMessage {
        val response = httpClient.post("$baseUrl/inserir") {
            header("Authorization", "Bearer $TOKEN")
            setBody(report)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun update(id: String, status: UpdateReportDTO): ResponseMessage {
        val response = httpClient.patch("$baseUrl/atualizar/$id") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $TOKEN")
            setBody(status)
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
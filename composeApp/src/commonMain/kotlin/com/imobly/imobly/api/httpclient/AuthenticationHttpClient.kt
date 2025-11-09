package com.imobly.imobly.api.httpclient

import com.imobly.imobly.api.TOKEN
import com.imobly.imobly.api.dto.ErrorDTO
import com.imobly.imobly.api.dto.Ok
import com.imobly.imobly.api.dto.ResponseMessage
import com.imobly.imobly.api.dto.TokenDTO
import com.imobly.imobly.domain.Auth
import com.imobly.imobly.domain.LandLord
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthenticationHttpClient(val httpClient: HttpClient) {
    val baseUrl = "/autenticacoes/locador"

    suspend fun signIn(auth: Auth): ResponseMessage {
        val response = httpClient.post("$baseUrl/logar") {
            contentType(ContentType.Application.Json)
            setBody(auth)
        }
        if (response.status.isSuccess()) {
            TOKEN = response.body<TokenDTO>().token
            return Ok()
        }
        return response.body<ErrorDTO>()
    }

    suspend fun signUp(landLord: LandLord): ResponseMessage {
        val response = httpClient.post("$baseUrl/cadastrar") {
            contentType(ContentType.Application.Json)
            setBody(landLord)
        }
        if (response.status.isSuccess()) {
            return Ok()
        }
        return response.body<ErrorDTO>()
    }
}
package com.imobly.imobly.api.tenant

import com.imobly.imobly.domain.ResponseMessage
import com.imobly.imobly.domain.Tenant
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

class TenantHttpClient (val httpClient: HttpClient) {

    val baseUrl = "/locatarios"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun searchAll(): List<Tenant> =
        httpClient.get("$baseUrl/encontrartodos")
            .body()

    suspend fun create(tenant: Tenant, image: GalleryPhotoResult?): ResponseMessage {

        val response = httpClient.post("$baseUrl/inserir") {
            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        key = "tenant",
                        value = json.encodeToString(tenant),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                    if (image != null) {
                        append(
                            key = "file",
                            value = image.loadBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"${image.fileName}\"")
                                append(HttpHeaders.ContentType, "multipart/form-data")
                            }
                        )
                    }
                }
            ))
        }

        if (response.status.isSuccess()) {
            return ResponseMessage()
        }
        return response.body<ResponseMessage>()
    }

    suspend fun update(id: String, tenant : Tenant, image: GalleryPhotoResult?): ResponseMessage {
        val response = httpClient.put("$baseUrl/atualizar/$id") {
            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        key = "tenant",
                        value = json.encodeToString(tenant),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                    if (image != null) {
                        append(
                            key = "file",
                            value = image.loadBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"${image.fileName}\"")
                                append(HttpHeaders.ContentType, "multipart/form-data")
                            }
                        )
                    }
                }
            ))
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
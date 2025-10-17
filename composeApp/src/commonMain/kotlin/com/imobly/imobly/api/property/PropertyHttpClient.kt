package com.imobly.imobly.api.property

import com.imobly.imobly.domain.Property
import com.imobly.imobly.domain.ResponseMessage
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

class PropertyHttpClient(val httpClient: HttpClient) {

    val baseUrl = "/propriedades"

    val json = Json {
        encodeDefaults = true
        explicitNulls = false
    }

    suspend fun searchAll(): List<Property> =
        httpClient.get("$baseUrl/encontrartodos")
            .body()

    suspend fun create(property: Property, images: List<GalleryPhotoResult>): ResponseMessage {
        val response = httpClient.post("$baseUrl/inserir") {
            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        key = "property",
                        value = json.encodeToString(property),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                    images.forEach {
                        append(
                            key = "files",
                            value = it.loadBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"${it.fileName}\"")
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

    suspend fun update(id: String, property : Property, images: List<GalleryPhotoResult>): ResponseMessage {
        val response = httpClient.put("$baseUrl/atualizar/$id") {
            setBody(MultiPartFormDataContent(
                formData {
                    append(
                        key = "property",
                        value = json.encodeToString(property),
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "application/json")
                        }
                    )
                    images.forEach {
                        append(
                            key = "files",
                            value = it.loadBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=\"${it.fileName}\"")
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


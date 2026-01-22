package id.derysudrajat.alif.data.remote.api

import id.derysudrajat.alif.data.remote.response.AyahResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class QuranApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.npoint.io/99c279bb173a6e28359c/"
    }

    suspend fun getSurah(no: Int): List<AyahResponse> {
        return client.get("${BASE_URL}surat/$no").body()
    }

}
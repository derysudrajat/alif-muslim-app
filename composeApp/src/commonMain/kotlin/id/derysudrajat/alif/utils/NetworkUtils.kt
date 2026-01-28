package id.derysudrajat.alif.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiException(val code: Int, message: String) : Exception(message)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Failed<out T>(val exception: ApiException) : ApiResult<T>()
    data object Loading : ApiResult<Nothing>()
}

object UseCase {
    suspend fun <T> execute(
        block: suspend () -> T,
        onResult: (ApiResult<T>) -> Unit
    ) {
        onResult(ApiResult.Loading)
        try {
            val response = withContext(Dispatchers.Default) { block() }
            onResult(ApiResult.Success(response))
        } catch (e: ApiException) {
            println("UseCase:Failed:ApiException: $e")
        } catch (e: Exception) {
            println("UseCase:Failed:Exception: $e")
        }
    }
}

object ApiUtils {
    suspend inline fun <reified T> validateResponse(response: HttpResponse): T {
        if (response.status.isSuccess()) {
            val resultBody = response.body<T>()
            println("ApiUtils:validateResponse:Success: $resultBody")
            return resultBody
        } else {
            println("ApiUtils:validateResponse:Failed: ${response.status}")
            throw ApiException(
                code = response.status.value,
                message = response.status.description
            )
        }
    }
}
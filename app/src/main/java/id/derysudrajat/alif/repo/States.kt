package id.derysudrajat.alif.repo

sealed class States<T> {
    class Loading<T> : States<T>()
    data class Success<T>(val data: T) : States<T>()
    data class Failed<T>(val message: String) : States<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
    }
}
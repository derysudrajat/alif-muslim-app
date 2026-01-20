package id.derysudrajat.alif

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
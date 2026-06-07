package dev.sushanthande.kclipboard

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
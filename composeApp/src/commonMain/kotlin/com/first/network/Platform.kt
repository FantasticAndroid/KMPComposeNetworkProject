package com.first.network

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
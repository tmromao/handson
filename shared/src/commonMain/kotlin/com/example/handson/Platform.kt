package com.example.handson

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
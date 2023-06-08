package com.example.data.repository.local

interface LocalRepository {
    fun getAnimeById(id: String): String
}

package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult

interface GeminiRepository {
    suspend fun generateContent(prompt: String): WorkResult<String?>
}
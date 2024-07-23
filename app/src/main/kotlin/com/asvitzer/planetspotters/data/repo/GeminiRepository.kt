package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import kotlinx.coroutines.flow.Flow

interface GeminiRepository {
    suspend fun generateContent(prompt: String): Flow<WorkResult<String?>>
}
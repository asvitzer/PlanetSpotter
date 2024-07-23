package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultGeminiRepository @Inject constructor(
    private val generativeModel: GenerativeModel
) : GeminiRepository {

    override suspend fun generateContent(prompt: String): Flow<WorkResult<String?>> {
        val response = generativeModel.generateContent(prompt)

        return response.text?.let { text ->
            flow {
                emit(WorkResult.Success(text))
            }
        } ?: run {
            flow {
                emit(WorkResult.Error(Exception("Empty Response")))
            }
        }
    }
}
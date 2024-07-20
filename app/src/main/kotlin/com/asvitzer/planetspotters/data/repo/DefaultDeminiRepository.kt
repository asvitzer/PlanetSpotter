package com.asvitzer.planetspotters.data.repo

import com.asvitzer.planetspotters.data.WorkResult
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

class DefaultGeminiRepository @Inject constructor(
    private val generativeModel: GenerativeModel
) : GeminiRepository {

    override suspend fun generateContent(prompt: String): WorkResult<String?> {
        val response = generativeModel.generateContent(prompt)

       return response.text?.let {
            WorkResult.Success(response.text)
        } ?: kotlin.run {
            WorkResult.Error(Exception("Empty Response from API"))
        }
    }
}
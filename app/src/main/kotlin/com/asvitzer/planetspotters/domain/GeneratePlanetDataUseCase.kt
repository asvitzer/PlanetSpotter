package com.asvitzer.planetspotters.domain

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.repo.GeminiRepository
import javax.inject.Inject

class GeneratePlanetDataUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {

    suspend operator fun invoke(prompt: String): WorkResult<String?> {
        return geminiRepository.generateContent(prompt)
    }
}
package com.asvitzer.planetspotters.domain

import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.repo.GeminiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeneratePlanetDataUseCase @Inject constructor(
    private val geminiRepository: GeminiRepository
) {
    companion object {
        const val FUN_FACTS_PROMPT: String = "Give me some fun facts, no more than 3, for this planet. If there are no fun facts, please say that there are none and give the reason why:"
    }

    suspend operator fun invoke(prompt: String): Flow<WorkResult<String?>> {
        return geminiRepository.generateContent("$FUN_FACTS_PROMPT $prompt")
    }
}
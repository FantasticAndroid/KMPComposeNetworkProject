package com.first.network

class CensorUseCase(private val censorRepository: ICensorRepository) {

    suspend operator fun invoke(text: String): Result<CensoredResponse> {
        return censorRepository.getCensoredText(text)
    }
}
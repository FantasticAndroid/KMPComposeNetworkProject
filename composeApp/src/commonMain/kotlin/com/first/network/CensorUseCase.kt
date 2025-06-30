package com.first.network

class CensorUseCase(private val censorRepository: ICensorRepository) {

    suspend operator fun invoke(text: String): Result<String> {
        return censorRepository.getCensoredText(text)
    }
}
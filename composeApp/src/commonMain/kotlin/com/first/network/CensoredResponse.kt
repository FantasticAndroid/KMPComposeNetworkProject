package com.first.network

import kotlinx.serialization.Serializable

@Serializable
data class CensoredResponse(val result: String)
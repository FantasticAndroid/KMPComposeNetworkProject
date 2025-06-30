package com.first.network

import kotlinx.serialization.Serializable

/**
 * Field should have default null value if mark nullable otherwise will get exception as below:
 * Exception in thread "AWT-EventQueue-0" io.ktor.serialization.JsonConvertException: Illegal input: Field 'error' is required for type with serial name 'com.first.network.CensoredResponse', but it was missing at path: $
 */
@Serializable
data class CensoredResponse(val result: String? = null, val error : String? = null)
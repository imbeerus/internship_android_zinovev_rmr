package com.lockwood.core.network.model.response

import androidx.annotation.Keep
import com.lockwood.core.network.common.JsonConstants
import com.squareup.moshi.Json

@Keep
data class CreateRequestTokenResponse(
    @Json(name = JsonConstants.SUCCESS)
    val success: Boolean, // true
    @Json(name = JsonConstants.EXPIRES_AT)
    val expiresAt: String, // 2016-08-26 17:04:39 UTC
    @Json(name = JsonConstants.REQUEST_TOKEN)
    val requestToken: String // 1531f1a558c8357ce8990cf887ff196e8f5402ec
)
package com.lockwood.themoviedb.login.remote.model.body

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CreateSessionBodyModel(
    @Json(name = "request_token")
    val requestToken: String // 1531f1a558c8357ce8990cf887ff196e8f5402ec
)
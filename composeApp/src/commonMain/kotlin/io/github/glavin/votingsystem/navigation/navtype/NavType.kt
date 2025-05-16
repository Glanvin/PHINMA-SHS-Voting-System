package io.github.glavin.votingsystem.navigation.navtype

import androidx.core.bundle.Bundle
import androidx.core.uri.Uri
import androidx.core.uri.UriUtils
import androidx.navigation.NavType
import io.github.glavin.votingsystem.features.auth.domain.Auth
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ChiroNavType {
    val AuthType = object : NavType<Auth>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Auth? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Auth {
            return Json.decodeFromString(UriUtils.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Auth) {
            return bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Auth): String {
            return UriUtils.encode(Json.encodeToString(value))
        }

    }
}
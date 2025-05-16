package io.github.glavin.votingsystem.features.auth.data.network

import io.github.glavin.votingsystem.core.data.network.Supabase
import io.github.glavin.votingsystem.core.data.network.Supabase.supabase
import io.github.glavin.votingsystem.features.auth.data.dto.AuthDto
import io.github.glavin.votingsystem.features.auth.data.dto.UserProfileDto
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SupabaseAuthDataSource(
    private val client: Supabase,
) : RemoteDataSource {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signIn(
        signInEmail: String,
        signInPassword: String
    ): Result<AuthDto> {
        return try {
            client.supabase.auth.signInWith(Email) {
                email = signInEmail
                password = signInPassword
            }

            val session = client.supabase.auth.currentSessionOrNull()
            val user = client.supabase.auth.currentUserOrNull()

            if (session == null || user == null) {
                println("Authentication failed: No active session or user found")
                return Result.failure(Exception("Authentication failed"))
            }

            println("Sign-in successful! User ID: ${user.id}")

            val uuid = Uuid.parse(user.id)

            val userInfo = AuthDto(
                id = uuid,
                sessionToken = session.accessToken
            )

            Result.success(userInfo)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signInWithToken(token: String): Result<AuthDto> {
        return try {
            client.supabase.auth.retrieveUser(token)

            val session = client.supabase.auth.currentSessionOrNull()
            val user = client.supabase.auth.currentUserOrNull()
            val newToken = client.supabase.auth.refreshSession(token)

            if (session == null || user == null) {
                println("Authentication failed: No active session or user found")
                return Result.failure(Exception("Authentication failed"))
            }


            val uuid = Uuid.parse(user.id)

            Result.success(
                AuthDto(
                    id = uuid,
                    sessionToken = newToken.toString(),
                    refreshToken = newToken.toString()
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUserProfile(userId: Uuid): Result<UserProfileDto> {
        return try {
            val result = client.supabase.postgrest
                .from("users")
                .select {
                    filter {
                        eq("id", userId.toString())
                    }
                }.decodeSingleOrNull<UserProfileDto>()
            if (result != null) {
                Result.success(result)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}
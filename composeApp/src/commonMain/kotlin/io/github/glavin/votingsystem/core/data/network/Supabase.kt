package io.github.glavin.votingsystem.core.data.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.coil.Coil3Integration
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object Supabase {

    val Supabase.supabase: SupabaseClient by lazy {
            createSupabaseClient(
                supabaseUrl = "https://npnsblrfgsfbofxhaxuk.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5wbnNibHJmZ3NmYm9meGhheHVrIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0MjkwNTY4NSwiZXhwIjoyMDU4NDgxNjg1fQ.7xTQBP68f_TNlRB5srN9e7VJ0Bk3U5bAHBjw-VrzMN8"
            ) {
                install(Postgrest)
                install(Auth)
                install(Storage)
                install(Coil3Integration)
            }
        }
}
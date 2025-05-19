package io.github.glavin.votingsystem.di

import io.github.glavin.votingsystem.core.data.network.Supabase
import io.github.glavin.votingsystem.core.ui.SnackbarController
import io.github.glavin.votingsystem.features.auth.data.datastore.AuthDataStore
import io.github.glavin.votingsystem.features.auth.data.datastore.LocalDataStoreSource
import io.github.glavin.votingsystem.features.auth.data.network.RemoteDataSource
import io.github.glavin.votingsystem.features.auth.data.network.SupabaseAuthDataSource
import io.github.glavin.votingsystem.features.auth.data.repository.DataStoreRepository
import io.github.glavin.votingsystem.features.auth.data.repository.SignInRepository
import io.github.glavin.votingsystem.features.auth.domain.AuthDataStoreRepository
import io.github.glavin.votingsystem.features.auth.domain.UserRepository
import io.github.glavin.votingsystem.features.auth.ui.sign.`in`.SignInViewModel
import io.github.glavin.votingsystem.features.auth.ui.sign.out.SignOutViewModel
import io.github.glavin.votingsystem.features.auth.ui.verification.forgot.password.ForgotPasswordViewModel
import io.github.glavin.votingsystem.features.home.ui.HomeViewModel
import io.github.glavin.votingsystem.features.voting.ui.list.CandidateListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformModule: Module

val sharedModule = module {
    single { Supabase }
    single { SnackbarController }

    singleOf(::SupabaseAuthDataSource).bind<RemoteDataSource>()
    singleOf(::SignInRepository).bind<UserRepository>()
    singleOf(::DataStoreRepository).bind<AuthDataStoreRepository>()
    singleOf(::AuthDataStore).bind<LocalDataStoreSource>()

    viewModelOf(::SignInViewModel)
    viewModelOf(::CandidateListViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SignOutViewModel)
}

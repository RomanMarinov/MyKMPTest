package di

import data.auth.local.AppPreferencesRepository
import data.auth.remote.AuthRepositoryImpl
import data.domofon.remote.DomofonRepositoryImpl
import data.home.remote.HomeRepositoryImpl
import data.outdoor.remote.OutdoorRepositoryImpl
import data.public_info.remote.CommonRepositoryImpl
import data.user_info.remote.UserInfoRepositoryImpl
import domain.repository.AuthRepository
import domain.repository.CommonRepository
import domain.repository.DomofonRepository
import domain.repository.HomeRepository
import domain.repository.OutdoorRepository
import domain.repository.UserInfoRepository
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val repositoryModule = module {

    single<OutdoorRepository> { OutdoorRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }
    single<CommonRepository> { CommonRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }
    single<DomofonRepository> { DomofonRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }

    single<HomeRepository> { HomeRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) } withOptions {
        createdAtStart()
    }

    single<UserInfoRepository> { UserInfoRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }

    single { AppPreferencesRepository(get()) }

  //  single { KtorAuthInterceptor(get(), get()) }


}


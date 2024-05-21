package di

import data.auth.local.AppPreferencesRepository
import data.auth.remote.AuthRepositoryImpl
import data.domofon.remote.DomofonRepositoryImpl
import data.home.remote.HomeRepositoryImpl
import data.outdoor.remote.OutdoorRepositoryImpl
import data.public_info.remote.PublicInfoRepositoryImpl
import domain.repository.AuthRepository
import domain.repository.DomofonRepository
import domain.repository.HomeRepository
import domain.repository.OutdoorRepository
import domain.repository.PublicInfoRepository
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val repositoryModule = module {
//    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<OutdoorRepository> { OutdoorRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }
    single<PublicInfoRepository> { PublicInfoRepositoryImpl(get()) } withOptions {
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


//
//        single {
//        getDataStore {
//            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
//                ?: throw Exception("Couldn't get Android Datastore context.")
//        }
  //  }
    single { AppPreferencesRepository(get()) }

}


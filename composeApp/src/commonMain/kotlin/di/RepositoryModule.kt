package di

import data.outdoor.remote.OutdoorRepositoryImpl
import data.public_info.remote.PublicInfoRepositoryImpl
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
}


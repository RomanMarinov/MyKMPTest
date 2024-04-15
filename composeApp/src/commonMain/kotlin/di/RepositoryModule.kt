package di

import data.outdoor.remote.OutdoorRepositoryImpl
import domain.repository.OutdoorRepository
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val repositoryModule = module {
//    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<OutdoorRepository> { OutdoorRepositoryImpl(get()) } withOptions {
        createdAtStart()
    }
}


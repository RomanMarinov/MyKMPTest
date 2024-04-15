package di

fun commonModule() = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)



//fun commonModule() = module {
//    single<HttpClient> { networkModule() }
//    single<OutdoorRepository>{ OutdoorRepositoryImpl(get()) }
//    factory { OutdoorScreenViewModel(get()) }
//
////    single {
////        DefaultRootComponent(
////            componentContext = get(),
////            outdoorScreenViewModel = get()
////        )
////    }
//}


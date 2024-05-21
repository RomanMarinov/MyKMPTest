package di

fun commonModule() = listOf(
    networkModule,
    repositoryModule,
    viewModelModule,
    getDatastoreModulePlatform()
)
package di

import org.koin.dsl.module
import presentation.ui.map_screen.MapScreenViewModel
import presentation.ui.outdoor_screen.OutdoorScreenViewModel

val viewModelModule = module {
    // factory определение, чтобы не хранить какие-либо экземпляры в памяти (избегайте утечек в жизненном цикле Android):
    // Функция get()позволяет попросить Koin разрешить необходимую зависимость.

//    factory { HomeScreenViewModel(get()) }
    factory { OutdoorScreenViewModel(get()) }
    factory { MapScreenViewModel(get()) }


}


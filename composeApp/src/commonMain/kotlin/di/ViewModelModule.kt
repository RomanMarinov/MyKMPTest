package di

import org.koin.dsl.module
import presentation.ui.domofon_screen.DomofonScreenViewModel
import presentation.ui.help_screen.HelpScreenViewModel
import presentation.ui.map_screen.MapScreenViewModel
//import presentation.ui.map_screen.MapScreenViewModel
import presentation.ui.outdoor_screen.OutdoorScreenViewModel

val viewModelModule = module {
    // factory определение, чтобы не хранить какие-либо экземпляры в памяти (избегайте утечек в жизненном цикле Android):
    // Функция get()позволяет попросить Koin разрешить необходимую зависимость.

//    factory { HomeScreenViewModel(get()) }
    factory { OutdoorScreenViewModel(get()) }
    factory { DomofonScreenViewModel(get()) }
    factory { MapScreenViewModel(get()) }
    factory { HelpScreenViewModel(get()) }

}


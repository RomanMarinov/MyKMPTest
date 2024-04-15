package di

import androidx.compose.runtime.Composable
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import presentation.ui.home_screen.HomeScreenViewModel
import presentation.ui.outdoor_screen.OutdoorScreenViewModel

val viewModelModule = module {
    // factory определение, чтобы не хранить какие-либо экземпляры в памяти (избегайте утечек в жизненном цикле Android):
    // Функция get()позволяет попросить Koin разрешить необходимую зависимость.

//    factory { HomeScreenViewModel(get()) }
    factory { OutdoorScreenViewModel(get()) }


}


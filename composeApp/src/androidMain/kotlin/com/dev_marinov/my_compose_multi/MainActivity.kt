package com.dev_marinov.my_compose_multi

import App
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {

//    private val module = module {
//        single<ComponentContext> {  }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {




            // https://stackoverflow.com/questions/78190854/status-bar-color-change-in-compose-multiplatform
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(
                    Color.TRANSPARENT, Color.TRANSPARENT
                ),
                navigationBarStyle = SystemBarStyle.light(
                    Color.TRANSPARENT, Color.TRANSPARENT
                )
            )
            // содержимое вашего приложения располагаться за системными элементами, такими как
            // StatusBar (строка состояния) или NavigationBar (панель навигации) в Android.
            WindowCompat.setDecorFitsSystemWindows(window, false)

            App()
        }
    }
}

@Composable
fun SetColorSystemBar() {

}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
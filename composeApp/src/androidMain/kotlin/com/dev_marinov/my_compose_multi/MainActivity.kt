package com.dev_marinov.my_compose_multi

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat


// навигация лакнера проще
// https://www.youtube.com/watch?v=AIC_OFQ1r3k

// room
// https://www.youtube.com/watch?v=IHs0yPa2Nv4

class MainActivity : ComponentActivity() {
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
            // сначала работало потом изменений не заметил
            WindowCompat.setDecorFitsSystemWindows(window, false)



            App()

        }
    }
}

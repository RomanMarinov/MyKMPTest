package com.dev_marinov.my_compose_multi

import App
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import co.touchlab.kermit.Logger


// навигация лакнера проще
// https://www.youtube.com/watch?v=AIC_OFQ1r3k

// room
// https://www.youtube.com/watch?v=IHs0yPa2Nv4

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Logger.d("4444 MainActivity(Home) loaded")
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
            LifecycleOwnerMainActivity()
        }
    }
}

@Composable
fun LifecycleOwnerMainActivity() {
    val localLifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = localLifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        Log.d("4444", " MainActivity Lifecycle.Event.ON_START")
                    }

                    Lifecycle.Event.ON_STOP -> { // когда свернул
                        Log.d("4444", " MainActivity Lifecycle.Event.ON_STOP")
                    }

                    Lifecycle.Event.ON_DESTROY -> { // когда удалил из стека
                        Log.d("4444", " MainActivity Lifecycle.Event.ON_DESTROY")
                    }

                    else -> {}
                }
            }
            localLifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                localLifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}

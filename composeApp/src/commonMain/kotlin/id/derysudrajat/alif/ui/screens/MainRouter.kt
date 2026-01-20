package id.derysudrajat.alif.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import id.derysudrajat.alif.ui.components.AppNav
import id.derysudrajat.alif.ui.components.AppNavigation
import id.derysudrajat.alif.ui.screens.main.HomeContent
import id.derysudrajat.alif.ui.screens.splash.SplashScreen
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

object MainNav {
    @Serializable
    data object Splash : NavKey

    @Serializable
    data object Home : NavKey
}

@OptIn(InternalSerializationApi::class)
@Composable
fun MainRouter() {
    val backStack = rememberNavBackStack(
        AppNav.getConfiguration {
            subclass(MainNav.Splash::class, MainNav.Splash::class.serializer())
            subclass(MainNav.Home::class, MainNav.Home::class.serializer())
        }, MainNav.Splash
    )

    AppNavigation(
        backStack = backStack,
        onBack = {},
        entryProvider = { key ->
            when (key) {
                is MainNav.Splash -> NavEntry(key) {
                    SplashScreen {
                        backStack.clear()
                        backStack.add(MainNav.Home)
                    }
                }

                is MainNav.Home -> NavEntry(key) {
                    HomeContent()
                }

                else -> NavEntry(key) { Text("Unknown route") }
            }
        }
    )
}

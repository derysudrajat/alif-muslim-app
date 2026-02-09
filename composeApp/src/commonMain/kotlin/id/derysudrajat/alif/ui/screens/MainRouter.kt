package id.derysudrajat.alif.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import id.derysudrajat.alif.services.Location
import id.derysudrajat.alif.ui.components.AppNav
import id.derysudrajat.alif.ui.components.AppNavigation
import id.derysudrajat.alif.ui.screens.calendar.IslamicCalendarScreen
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

    @Serializable
    data class Calendar(
        val latitude: Double,
        val longitude: Double
    ) : NavKey
}

@OptIn(InternalSerializationApi::class)
@Composable
fun MainRouter() {
    val backStack = rememberNavBackStack(
        AppNav.getConfiguration {
            subclass(MainNav.Splash::class, MainNav.Splash::class.serializer())
            subclass(MainNav.Home::class, MainNav.Home::class.serializer())
            subclass(MainNav.Calendar::class, MainNav.Calendar::class.serializer())
        }, MainNav.Splash
    )

    AppNavigation(
        backStack = backStack,
        onBack = backStack::removeLastOrNull,
        entryProvider = { key ->
            when (key) {
                is MainNav.Splash -> NavEntry(key) {
                    SplashScreen {
                        backStack.clear()
                        backStack.add(MainNav.Home)
                    }
                }

                is MainNav.Home -> NavEntry(key) {
                    HomeContent(
                        goToCalendar = {
                            backStack.add(MainNav.Calendar(it.latitude, it.longitude))
                        }
                    )
                }

                is MainNav.Calendar -> NavEntry(key) {
                    IslamicCalendarScreen(
                        location = Location(key.latitude, key.longitude),
                        onBack = backStack::removeLastOrNull
                    )
                }

                else -> NavEntry(key) { Text("Unknown route") }
            }
        }
    )
}

@Composable
fun CalendarContent() {
    TODO("Not yet implemented")
}

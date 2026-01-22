import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        KoinInitKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
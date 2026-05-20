import SwiftUI
import SharedLogic

@main
struct iOSApp: App {
    @StateObject private var viewModel = PokedexViewModel(pokedex: PokeSDK())

    var body: some Scene {
        WindowGroup {
            PokedexView(viewModel: viewModel)
        }
    }
}

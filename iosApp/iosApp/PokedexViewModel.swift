// Created by Juan Acosta on 20/05/26.

import Foundation
import SharedLogic

@MainActor
class PokedexViewModel: ObservableObject {

    private let pokedex: PokeSDK

    @Published var pokemon: Pokemon? = nil
    @Published var errorMessage: String? = nil

    var typesForDisplay: [String] { (pokemon?.types as? [String]) ?? [] }
    var abilitiesForDisplay: [String] { (pokemon?.abilities as? [String]) ?? [] }

    func statValue(for key: String) -> Int {
        guard let stat = pokemon?.stats[key] else { return 0 }
        return Int(stat.int32Value)
    }

    init(pokedex: PokeSDK) {
        self.pokedex = pokedex
        findPokemon(name: "pikachu")
    }

    func findPokemon(name: String) {
        pokedex.getPokemonDetailsUseCase.execute(name: name) { result, error in
            Task { @MainActor [weak self] in
                guard let self else { return }
                if let pokemon = result {
                    self.pokemon = pokemon
                    self.errorMessage = nil
                } else if let error = error {
                    self.errorMessage = error.localizedDescription
                }
            }
        }
    }
}

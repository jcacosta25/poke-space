// Created by Juan Acosta on 20/05/26.

import SwiftUI
import SharedLogic

struct PokedexView: View {
    @ObservedObject var viewModel: PokedexViewModel
    @State private var searchQuery = ""

    var body: some View {
        VStack(spacing: 16) {

            HStack {
                Circle()
                    .fill(Color(red: 0.13, green: 0.59, blue: 0.95))
                    .frame(width: 50, height: 50)
                    .overlay(Circle().stroke(Color.white, lineWidth: 3))

                HStack(spacing: 6) {
                    Circle().fill(Color.red).frame(width: 10, height: 10)
                    Circle().fill(Color.yellow).frame(width: 10, height: 10)
                    Circle().fill(Color.green).frame(width: 10, height: 10)
                }
                Spacer()
            }
            .padding(.horizontal)

            // Main display
            ZStack {
                RoundedRectangle(cornerRadius: 12)
                    .fill(Color(red: 0.88, green: 0.88, blue: 0.88))

                RoundedRectangle(cornerRadius: 8)
                    .fill(Color.white)
                    .padding(16)
                    .overlay(
                        Group {
                            if let pokemon = viewModel.pokemon {
                                AsyncImage(url: URL(string: pokemon.imageUrl)) { image in
                                    image.resizable().scaledToFit()
                                } placeholder: {
                                    ProgressView()
                                }
                                .padding(30)
                            } else {
                                ProgressView()
                            }
                        }
                    )
            }
            .frame(maxHeight: .infinity)

            VStack(spacing: 8) {

                HStack {
                    Text(viewModel.pokemon?.name.uppercased() ?? "BUSCANDO...")
                        .font(.system(.body, design: .monospaced)).bold()
                    Spacer()
                    Text("ID: #\(viewModel.pokemon?.id ?? 0)")
                        .font(.system(.body, design: .monospaced))
                }
                .padding(10)
                .background(Color(red: 0.32, green: 0.68, blue: 0.38))
                .cornerRadius(6)
                .foregroundColor(.black)

                VStack(alignment: .leading, spacing: 4) {
                    if let pokemon = viewModel.pokemon {
                        HStack {
                            ForEach(viewModel.typesForDisplay, id: \.self) { type in
                                Text(type.uppercased())
                                    .font(.system(size: 9, design: .monospaced))
                                    .fontWeight(.bold)
                                    .foregroundColor(.white)
                                    .padding(.horizontal, 5)
                                    .padding(.vertical, 2)
                                    .background(typeColor(type))
                                    .cornerRadius(4)
                            }
                            Spacer()
                            Text(String(format: "%.1fm  %.1fkg",
                                        Float(pokemon.height) / 10,
                                        Float(pokemon.weight) / 10))
                                .font(.system(size: 9, design: .monospaced))
                                .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                        }

                        // Stats
                        let statKeys: [(String, String)] = [
                            ("hp", "HP"), ("attack", "ATK"), ("defense", "DEF"),
                            ("special-attack", "SpA"), ("special-defense", "SpD"), ("speed", "SPD")
                        ]
                        ForEach(statKeys, id: \.0) { key, label in
                            let value = viewModel.statValue(for: key)
                            HStack(spacing: 4) {
                                Text(label)
                                    .font(.system(size: 9, design: .monospaced))
                                    .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                                    .frame(width: 28, alignment: .leading)
                                GeometryReader { geo in
                                    ZStack(alignment: .leading) {
                                        RoundedRectangle(cornerRadius: 3)
                                            .fill(Color(red: 0.23, green: 0.35, blue: 0.23))
                                        RoundedRectangle(cornerRadius: 3)
                                            .fill(Color(red: 0.32, green: 0.68, blue: 0.38))
                                            .frame(width: geo.size.width * CGFloat(value) / 255)
                                    }
                                }
                                .frame(height: 6)
                                Text("\(value)")
                                    .font(.system(size: 9, design: .monospaced))
                                    .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                                    .frame(width: 24, alignment: .trailing)
                            }
                        }

                        Text("ABL: \(viewModel.abilitiesForDisplay.joined(separator: ", "))")
                            .font(.system(size: 9, design: .monospaced))
                            .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                    } else {
                        Text("Ingrese comando...")
                            .font(.system(.caption, design: .monospaced))
                            .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(8)
                .background(Color(red: 0.54, green: 0.75, blue: 0.54))
                .cornerRadius(6)

                // Search field
                HStack {
                    TextField("Buscar Pokémon...", text: $searchQuery)
                        .font(.system(.body, design: .monospaced))
                        .foregroundColor(Color(red: 0.1, green: 0.2, blue: 0.1))
                        .onSubmit {
                            if !searchQuery.isEmpty {
                                viewModel.findPokemon(name: searchQuery)
                            }
                        }
                }
                .padding(8)
                .background(Color(red: 0.54, green: 0.75, blue: 0.54))
                .cornerRadius(6)

                HStack {
                    Rectangle()
                        .fill(Color.black)
                        .frame(width: 45, height: 45)

                    Spacer()

                    HStack(spacing: 12) {
                        Circle().fill(Color.red).frame(width: 25, height: 25)
                        Circle().fill(Color.black).frame(width: 25, height: 25)
                    }
                }
                .padding(.top, 4)
            }
            .padding(16)
            .background(Color(red: 0.15, green: 0.15, blue: 0.15))
            .cornerRadius(12)
        }
        .padding()
        .background(Color(red: 0.86, green: 0.04, blue: 0.18).edgesIgnoringSafeArea(.all))
    }

    private func typeColor(_ type: String) -> Color {
        switch type.lowercased() {
        case "fire":     return Color(red: 1.0,  green: 0.42, blue: 0.21)
        case "water":    return Color(red: 0.27, green: 0.53, blue: 1.0)
        case "grass":    return Color(red: 0.30, green: 0.69, blue: 0.31)
        case "electric": return Color(red: 1.0,  green: 0.84, blue: 0.0)
        case "psychic":  return Color(red: 1.0,  green: 0.25, blue: 0.51)
        case "ice":      return Color(red: 0.50, green: 0.87, blue: 0.92)
        case "dragon":   return Color(red: 0.42, green: 0.11, blue: 0.60)
        case "dark":     return Color(red: 0.29, green: 0.14, blue: 0.35)
        case "fairy":    return Color(red: 0.96, green: 0.56, blue: 0.69)
        case "fighting": return Color(red: 0.78, green: 0.16, blue: 0.16)
        case "flying":   return Color(red: 0.49, green: 0.73, blue: 0.91)
        case "poison":   return Color(red: 0.61, green: 0.15, blue: 0.69)
        case "ground":   return Color(red: 0.83, green: 0.65, blue: 0.42)
        case "rock":     return Color(red: 0.62, green: 0.62, blue: 0.62)
        case "bug":      return Color(red: 0.55, green: 0.76, blue: 0.29)
        case "ghost":    return Color(red: 0.31, green: 0.16, blue: 0.66)
        case "steel":    return Color(red: 0.47, green: 0.57, blue: 0.60)
        default:         return Color(red: 0.62, green: 0.62, blue: 0.62)
        }
    }
}

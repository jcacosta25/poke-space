package com.jcacosta.pokespace

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.jcacosta.pokespace.domain.model.Pokemon
import org.koin.androidx.compose.koinViewModel

private val statLabels = linkedMapOf(
    "hp" to "HP",
    "attack" to "ATK",
    "defense" to "DEF",
    "special-attack" to "SpA",
    "special-defense" to "SpD",
    "speed" to "SPD"
)

private fun typeColor(type: String): Color = when (type.lowercase()) {
    "fire" -> Color(0xFFFF6B35)
    "water" -> Color(0xFF4488FF)
    "grass" -> Color(0xFF4CAF50)
    "electric" -> Color(0xFFFFD700)
    "psychic" -> Color(0xFFFF4081)
    "ice" -> Color(0xFF80DEEA)
    "dragon" -> Color(0xFF6A1B9A)
    "dark" -> Color(0xFF4A235A)
    "fairy" -> Color(0xFFF48FB1)
    "fighting" -> Color(0xFFC62828)
    "flying" -> Color(0xFF7CB9E8)
    "poison" -> Color(0xFF9C27B0)
    "ground" -> Color(0xFFD4A76A)
    "rock" -> Color(0xFF9E9E9E)
    "bug" -> Color(0xFF8BC34A)
    "ghost" -> Color(0xFF512DA8)
    "steel" -> Color(0xFF78909C)
    else -> Color(0xFF9E9E9E)
}

@Composable
fun Pokedex(viewModel: PokedexViewModel = koinViewModel()) {
    val pokemon by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDC0A2D))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2196F3))
                    .border(3.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Red))
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Yellow))
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(Color.Green))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE0E0E0))
                .padding(16.dp)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            pokemon?.let {
                AsyncImage(
                    model = it.imageUrl,
                    contentDescription = it.name,
                    modifier = Modifier.fillMaxSize(0.8f)
                )
            } ?: CircularProgressIndicator(color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF262626))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF51AD60))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = pokemon?.name?.uppercase() ?: "BUSCANDO...",
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 18.sp, color = Color.Black)
                )
                Text(
                    text = "ID: #${pokemon?.id ?: "???"}",
                    style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 16.sp, color = Color.Black)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF8BBE8A))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (pokemon != null) {
                    PokemonInfoPanel(pokemon!!)
                } else {
                    Text(
                        text = "Ingrese comando...",
                        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 12.sp, color = Color(0xFF1A331A))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF8BBE8A))
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp,
                        color = Color(0xFF1A331A)
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (searchQuery.isNotBlank()) viewModel.findPokemon(searchQuery)
                    }),
                    decorationBox = { innerTextField ->
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Buscar Pokémon...",
                                style = TextStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 14.sp,
                                    color = Color(0xFF3A5A3A)
                                )
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(50.dp).background(Color.Black))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.Red))
                    Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color.Black))
                }
            }
        }
    }
}

@Composable
private fun PokemonInfoPanel(pokemon: Pokemon) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            pokemon.types.forEach { type ->
                Text(
                    text = type.uppercase(),
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(typeColor(type))
                        .padding(horizontal = 5.dp, vertical = 2.dp),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "${"%.1f".format(pokemon.height / 10f)}m  ${"%.1f".format(pokemon.weight / 10f)}kg",
            style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Color(0xFF1A331A))
        )
    }

    statLabels.forEach { (key, label) ->
        val value = pokemon.stats[key] ?: 0
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                modifier = Modifier.width(28.dp),
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Color(0xFF1A331A))
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0xFF3A5A3A))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(value / 255f)
                        .background(Color(0xFF51AD60))
                )
            }
            Text(
                text = "$value",
                modifier = Modifier.width(24.dp),
                style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Color(0xFF1A331A))
            )
        }
    }

    Text(
        text = "ABL: ${pokemon.abilities.joinToString(", ")}",
        style = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Color(0xFF1A331A))
    )
}

package com.jcacosta.pokespace

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { PokeSDK() }
    viewModel { PokedexViewModel(get()) }
}

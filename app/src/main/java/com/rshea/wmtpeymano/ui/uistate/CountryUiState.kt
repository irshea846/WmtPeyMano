package com.rshea.wmtpeymano.ui.uistate

data class CountryUiState(
    val isFetchingData: Boolean = false,
    val countriesItems: List<CountryItemUiState> = listOf(),
    val userMessages: String
)

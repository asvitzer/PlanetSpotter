package com.asvitzer.planetspotters.ui.model

import com.asvitzer.planetspotters.data.model.Planet

data class PlanetsListUiState (
    val planets: List<Planet> = emptyList(),
    override var isLoading: Boolean = false,
    override var isError: Boolean = false
): UiState
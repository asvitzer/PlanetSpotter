package com.asvitzer.planetspotters.ui.model

import java.util.Date

data class AddEditPlanetUiState(
    val planetName: String = "",
    val planetDistanceLy: Float = 1.0F,
    val planetDiscovered: Date = Date(),
    val isLoading: Boolean = false,
    val isPlanetSaved: Boolean = false,
    val isPlanetSaving: Boolean = false,
    val planetSavingError: Int? = null
)
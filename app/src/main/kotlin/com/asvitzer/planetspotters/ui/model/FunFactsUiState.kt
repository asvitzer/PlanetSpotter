package com.asvitzer.planetspotters.ui.model

import java.util.Date

data class FunFactsUiState(
    val funFacts: String = "",
    override var isLoading: Boolean = false,
    override var isError: Boolean = false
) : UiState
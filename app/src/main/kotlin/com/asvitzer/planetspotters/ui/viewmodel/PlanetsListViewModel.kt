package com.asvitzer.planetspotters.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import com.asvitzer.planetspotters.ui.model.PlanetsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlanetsListViewModel @Inject constructor(
    private val addPlanetUseCase: AddPlanetUseCase,
    private val planetsRepository: PlanetsRepository
): ViewModel() {
    private val planets = planetsRepository.getPlanetsFlow()

    val uiState = planets.map { planets ->
        when (planets) {
            is WorkResult.Error -> PlanetsListUiState(isError = true)
            is WorkResult.Loading -> PlanetsListUiState(isLoading = true)
            is WorkResult.Success -> PlanetsListUiState(planets = planets.data)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlanetsListUiState(isLoading = true)
    )

    fun addSamplePlanets() {
        viewModelScope.launch {
            val planets = arrayOf(
                Planet(name = "Skaro", distanceLy = 0.5F, discovered = Date()),
                Planet(name = "Trenzalore", distanceLy = 5F, discovered = Date()),
                Planet(name = "Galifrey", distanceLy = 80F, discovered = Date()),
            )
            planets.forEach { addPlanetUseCase(it) }
        }
    }

    fun deletePlanet(planetId: String) {
        viewModelScope.launch {
            planetsRepository.deletePlanet(planetId)
        }
    }

    fun refreshPlanetsList() {
        viewModelScope.launch {
            planetsRepository.refreshPlanets()
        }
    }
}
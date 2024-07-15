package com.asvitzer.planetspotters.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import com.asvitzer.planetspotters.ui.model.PlanetsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PlanetsListViewModel @Inject constructor(
    private val addPlanetUseCase: AddPlanetUseCase,
    private val planetsRepository: PlanetsRepository
) : ViewModel() {
    private val planets = planetsRepository.getPlanetsFlow()

    //How many things are we waiting for to load?
    private val numLoadingItems = MutableStateFlow(0)

    val uiState = combine(planets, numLoadingItems) { planets, loadingItems ->
        when (planets) {
            is WorkResult.Error -> PlanetsListUiState(isError = true)
            is WorkResult.Loading -> PlanetsListUiState(isLoading = true)
            is WorkResult.Success -> PlanetsListUiState(
                planets = planets.data,
                isLoading = loadingItems > 0
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlanetsListUiState(isLoading = true)
    )

    fun addSamplePlanets() {
        viewModelScope.launch {
            withLoading {
                val planets = arrayOf(
                    Planet(name = "Skaro", distanceLy = 0.5F, discovered = Date()),
                    Planet(name = "Trenzalore", distanceLy = 5F, discovered = Date()),
                    Planet(name = "Galifrey", distanceLy = 80F, discovered = Date()),
                )
                planets.forEach { addPlanetUseCase(it) }
            }
        }
    }

    fun deletePlanet(planetId: String) {
        viewModelScope.launch {
            withLoading {
                planetsRepository.deletePlanet(planetId)
            }
        }
    }

    fun refreshPlanetsList() {
        viewModelScope.launch {
            withLoading {
                planetsRepository.refreshPlanets()
            }
        }
    }

    private suspend fun withLoading(runCode: suspend () -> Unit) {
        try {
            addLoadingElement()
            runCode()
        } finally {
            removeLoadingElement()
        }
    }

    private fun addLoadingElement() = numLoadingItems.getAndUpdate { num -> num + 1 }
    private fun removeLoadingElement() = numLoadingItems.getAndUpdate { num -> num - 1 }
}
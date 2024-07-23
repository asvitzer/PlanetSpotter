package com.asvitzer.planetspotters.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asvitzer.planetspotters.R
import com.asvitzer.planetspotters.data.WorkResult
import com.asvitzer.planetspotters.data.model.Planet
import com.asvitzer.planetspotters.data.repo.PlanetsRepository
import com.asvitzer.planetspotters.di.IoDispatcher
import com.asvitzer.planetspotters.domain.AddPlanetUseCase
import com.asvitzer.planetspotters.domain.GeneratePlanetDataUseCase
import com.asvitzer.planetspotters.navigation.PlanetsDestinationsArgs
import com.asvitzer.planetspotters.ui.model.AddEditPlanetUiState
import com.asvitzer.planetspotters.ui.model.FunFactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPlanetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val planetsRepository: PlanetsRepository,
    private val addPlanetUseCase: AddPlanetUseCase,
    private val generateContentUseCase: GeneratePlanetDataUseCase,
    @IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher
): ViewModel() {
    private val planetId: String? = savedStateHandle[PlanetsDestinationsArgs.PLANET_ID_ARG]

    private val _uiStatePlanet = MutableStateFlow(AddEditPlanetUiState())
    val uiStatePlanet: StateFlow<AddEditPlanetUiState> = _uiStatePlanet.asStateFlow()

    private val _uiStateFunFacts = MutableStateFlow(FunFactsUiState())
    val uiStateFunFacts: StateFlow<FunFactsUiState> = _uiStateFunFacts.asStateFlow()

    init {
        if (planetId != null) {
            loadPlanet(planetId)
        }
    }

    private fun loadPlanet(planetId: String) {
        _uiStatePlanet.update {
            it.copy(isLoading = true) }
        viewModelScope.launch {
            val result = planetsRepository.getPlanetFlow(planetId).first()
            if (result !is WorkResult.Success || result.data == null) {
                _uiStatePlanet.update { it.copy(isLoading = false) }
            }
            else {
                val planet = result.data
                _uiStatePlanet.update {
                    it.copy(
                        isLoading = false,
                        planetName = planet.name,
                        planetDistanceLy = planet.distanceLy,
                        planetDiscovered = planet.discovered
                    )
                }
            }
        }
    }

    fun setPlanetName(name: String) {
        _uiStatePlanet.update { it.copy(planetName = name) }
    }

    fun getFunFacts() {
        _uiStateFunFacts.update {
            it.copy(isLoading = true) }

        viewModelScope.launch(ioCoroutineDispatcher) {
            val funFacts = generateContentUseCase(_uiStatePlanet.value.planetName).first()

            when {
                funFacts is WorkResult.Error -> {
                    _uiStateFunFacts.update { it.copy(isLoading = false, isError = true) }
                }
                funFacts is WorkResult.Loading -> {
                    _uiStateFunFacts.update { it.copy(isLoading = true) }
                }
                funFacts !is WorkResult.Success || funFacts.data.isNullOrEmpty() -> {
                    _uiStateFunFacts.update { it.copy(isLoading = false, isError = true) }
                }
                else -> {
                    _uiStateFunFacts.update { it.copy(isLoading = false,
                        isError = false,
                        funFacts = funFacts.data) }
                }
            }
        }
    }

    fun setPlanetDistanceLy(distanceLy: Float) {
        _uiStatePlanet.update { it.copy(planetDistanceLy = distanceLy) }
    }

    fun savePlanet() {
        viewModelScope.launch {
            try {
                _uiStatePlanet.update { it.copy(isPlanetSaving = true) }
                addPlanetUseCase(
                    Planet(
                        planetId = planetId,
                        name = _uiStatePlanet.value.planetName,
                        distanceLy = uiStatePlanet.value.planetDistanceLy,
                        discovered = uiStatePlanet.value.planetDiscovered
                    )
                )
                _uiStatePlanet.update { it.copy(isPlanetSaved = true) }
            }
            catch (e: Exception) {
                _uiStatePlanet.update { it.copy(planetSavingError = R.string.error_saving_planet) }
            }
            finally {
                _uiStatePlanet.update { it.copy(isPlanetSaving = false) }
            }
        }
    }
}
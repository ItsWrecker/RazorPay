package com.wrecker.razorpay.presentatation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.interactor.SearchMovieUseCase
import com.wrecker.razorpay.domain.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state


    fun handleUserEvent(action: SearchAction) = viewModelScope.launch {
        try {
            when (action) {
                is SearchAction.Search -> {
                    searchMovieUseCase(action.query).collectLatest { event ->
                        when (event) {
                            is Event.Errors -> _state.value = _state.value.copy(
                                error = event.message
                            )

                            Event.Loading -> _state.value = _state.value.copy(isLoading = true)
                            is Event.Success -> {
                                val data = event.data
                                if (data.isNotEmpty())
                                    _state.value =
                                        _state.value.copy(movieList = data, isLoading = false)
                            }
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
package com.wrecker.razorpay.presentatation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wrecker.razorpay.domain.Event
import com.wrecker.razorpay.domain.interactor.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {


    private val _state  = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state

    fun getDetails(imdbId: String) = viewModelScope.launch {
        getMovieDetailsUseCase(imdbId).collectLatest {event ->
            when(event){
                is Event.Errors -> _state.value = _state.value.copy(error = event.message)
                Event.Loading -> _state.value = _state.value.copy(isLoading = true)
                is Event.Success -> {
                    val data = event.data
                    _state.value = _state.value.copy(
                        movie = data,
                        isLoading = false
                    )
                }
            }
        }
    }
}
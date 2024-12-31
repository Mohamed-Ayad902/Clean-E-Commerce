package com.mayad7474.cleane_commerce.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface State // Represents UI states
interface Intent // Represents user intents or actions

abstract class BaseViewModel<S : State, I : Intent> : ViewModel() {

    private val _viewState = MutableSharedFlow<S>()
    val viewState: SharedFlow<S> = _viewState

    private val intentChannel = Channel<I>(Channel.UNLIMITED) // Processes incoming intents
    private val intents = intentChannel.receiveAsFlow()

    internal fun produce(s: S) {
        viewModelScope.launch(Dispatchers.Main) {
            _viewState.emit(s)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.Main) {
            intents.collect { handleIntent(it) }
        }
    }

    // To be implemented by child ViewModels to handle specific intents
    protected abstract fun handleIntent(intent: I)

    // To be called from the ui to dispatch user intents
    fun sendIntent(intent: I) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

}
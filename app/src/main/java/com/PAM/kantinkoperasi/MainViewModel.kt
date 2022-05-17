package com.PAM.kantinkoperasi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    private val _is_loading = MutableStateFlow(true)
    val isLoading = _is_loading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(400)
            _is_loading.value = false

        }
    }
}
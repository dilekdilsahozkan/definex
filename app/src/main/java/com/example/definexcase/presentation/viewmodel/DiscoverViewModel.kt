package com.example.definexcase.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.definexcase.base.BaseResult
import com.example.definexcase.base.BaseViewModel
import com.example.definexcase.base.ViewState
import com.example.definexcase.data.dto.MarketDto
import com.example.definexcase.domain.DiscoverUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val useCase: DiscoverUseCase
): BaseViewModel<MarketDto>() {

    private val _horizontalState: MutableStateFlow<ViewState<MarketDto>> = MutableStateFlow(ViewState.Idle())
    val horizontalState: StateFlow<ViewState<MarketDto>> = _horizontalState

    private val _secondHorizontalState: MutableStateFlow<ViewState<MarketDto>> = MutableStateFlow(ViewState.Idle())
    val secondHorizontalState: StateFlow<ViewState<MarketDto>> = _secondHorizontalState

    private val _verticalState: MutableStateFlow<ViewState<MarketDto>> = MutableStateFlow(ViewState.Idle())
    val verticalState: StateFlow<ViewState<MarketDto>> = _verticalState

    fun getHorizontal(token: String) {
        viewModelScope.launch {
            useCase.getHorizontal(token)
                .onStart {
                    _horizontalState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _horizontalState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _horizontalState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _horizontalState.value = ViewState.Error()
                    }
                }
        }
    }

    fun getSecondHorizontal(token: String) {
        viewModelScope.launch {
            useCase.getSecondHorizontal(token)
                .onStart {
                    _secondHorizontalState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _secondHorizontalState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _secondHorizontalState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _secondHorizontalState.value = ViewState.Error()
                    }
                }
        }
    }

    fun getColumnList(token: String) {
        viewModelScope.launch {
            useCase.getColumnList(token)
                .onStart {
                    _verticalState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _verticalState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _verticalState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _verticalState.value = ViewState.Error()
                    }
                }
        }
    }
}
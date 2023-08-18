package com.example.definexcase.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.definexcase.base.BaseResult
import com.example.definexcase.base.BaseViewModel
import com.example.definexcase.base.ViewState
import com.example.definexcase.data.dto.LoginDto
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase
): BaseViewModel<LoginDto>() {

    private val _loginState: MutableStateFlow<ViewState<LoginDto>> = MutableStateFlow(ViewState.Idle())
    val loginState: StateFlow<ViewState<LoginDto>> = _loginState

    fun login(loginRequest: LoginRequestDto) {
        viewModelScope.launch {
            useCase.login(loginRequest)
                .onStart {
                    _loginState.value = ViewState.Loading()
                }
                .catch { exception ->
                    _loginState.value = ViewState.Error(message = exception.message)
                    Log.e("CATCH", "exception : $exception")
                }
                .collect { baseResult ->
                    when (baseResult) {
                        is BaseResult.Success ->
                            _loginState.value = ViewState.Success(baseResult.data)
                        is BaseResult.Error ->
                            _loginState.value = ViewState.Error()
                    }
                }
        }
    }
}
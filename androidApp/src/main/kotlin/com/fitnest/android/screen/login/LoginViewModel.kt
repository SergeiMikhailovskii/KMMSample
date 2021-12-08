package com.fitnest.android.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fitnest.android.base.BaseViewModel
import com.fitnest.domain.entity.LoginData
import com.fitnest.state.LoginResultState
import com.fitnest.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginResultLiveData = MutableLiveData<LoginResultState>()
    internal val loginResultLiveData: LiveData<LoginResultState> = _loginResultLiveData

    internal fun loginUser(login: String, password: String) {
        loginUseCase(LoginData(login, password)) {
            it.either(::handleFailure) {
                _loginResultLiveData.value = LoginResultState.LOGIN_SUCCESS
            }
        }
    }

    internal fun dismissLoginDialog() {
        _loginResultLiveData.value = LoginResultState.LOGIN_DEFAULT
    }

}
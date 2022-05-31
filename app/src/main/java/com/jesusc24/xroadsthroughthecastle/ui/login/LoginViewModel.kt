package com.jesusc24.xroadsthroughthecastle.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.model.User
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils

sealed class State {
    data class EmailError(val message: Int) : State()
    data class PasswordError(val message: Int) : State()
    data class Loading(val process : Boolean) : State()
    data class ResetError(val process: Boolean) : State()

    //Estos dos estados se definen después de la respuesta del reposiorio
    //se controlan a través de la interfaz OnRepositoryCallback
    data class AuthError(val message: Int) : State()
    data class Success(val message: Int) : State()

}

class LoginViewModel : ViewModel(), OnRepositoryCallback {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    var state = MutableLiveData<State>()

    fun validateCredentials() {
        when {
            email.value == null -> state.postValue(State.EmailError(R.string.errEmailEmpty))
            !(CommonUtils.isEmailValid(email.value)) -> state.postValue(State.EmailError(R.string.errEmail))
            password.value == null -> state.postValue(State.PasswordError(R.string.errPasswordEmpty))
            !(CommonUtils.isPasswordValid(password.value)) -> state.postValue(State.PasswordError(R.string.errPasswordIncorrect))
            else -> {
                state.postValue(State.Loading(true))
                UserRepository.getInstance(this).login(User(email.value.toString(), password.value.toString()))
            }
        }
    }

    //region Métodos callback del repositorio
    override fun onSuccess() {
        state.postValue(State.Success(R.string.on_sucess_login))
    }

    override fun onFailure() {
        state.postValue(State.AuthError(R.string.err_auth))
    }
    //endregion


}
package com.jesusc24.xroadsthroughthecastle.ui.singUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesusc24.xroadsthroughthecastle.R
import com.jesusc24.xroadsthroughthecastle.data.model.User
import com.jesusc24.xroadsthroughthecastle.data.repository.UserRepository
import com.jesusc24.xroadsthroughthecastle.ui.base.OnRepositoryCallback
import com.jesusc24.xroadsthroughthecastle.ui.configuracion.UserFragment
import com.jesusc24.xroadsthroughthecastle.utils.CommonUtils

sealed class State {
    data class NameEmptyError(val message: Int) : State()
    data class EmailError(val message: Int) : State()
    data class PasswordError(val message: Int) : State()
    data class ConfirmPasswordError(val message: Int) : State()
    data class Loading(val process : Boolean) : State()

    //Estos dos estados se definen después de la respuesta del reposiorio
    //se controlan a través de la interfaz OnRepositoryCallback
    data class AuthError(val message: Int) : State()
    data class Success(val message: Int) : State()
}

class SignUpViewModel : ViewModel(), OnRepositoryCallback {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirmPassword = MutableLiveData<String>()
    var state = MutableLiveData<State>()


    fun validateCredentials() {

        val imagen = SignUpActivity.encodedImage

        when {
            name.value == null -> state.postValue(State.NameEmptyError(R.string.errNameEmpty))
            email.value == null -> state.postValue(State.EmailError(R.string.errEmailEmpty))
            !(CommonUtils.isEmailValid(email.value)) -> state.postValue(State.EmailError(R.string.errEmail))
            password.value == null -> state.postValue(State.PasswordError(R.string.errPasswordEmpty))
            !(CommonUtils.isPasswordValid(password.value)) -> state.postValue(State.PasswordError(R.string.errPasswordIncorrect))
            confirmPassword.value == null -> state.postValue(State.ConfirmPasswordError(R.string.errConfirmPasswordEmpty))
            !(password.value.equals(confirmPassword.value)) -> state.postValue(State.ConfirmPasswordError(R.string.errConfirmPassword))
            else -> {
                state.postValue(State.Loading(true))
                UserRepository.getInstance(this).signUp(User(email.value.toString(), password.value.toString(), name.value.toString(), imagen, ""))
            }
        }
    }

    //region Métodos callback del repositorio
    override fun onSuccess() {
        state.postValue(State.Success(R.string.on_sucess_login))
    }

    override fun onFailure(message : Int) {
        state.postValue(State.AuthError(message))
    }
    //endregion


}


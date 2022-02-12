package com.jesusc24.xroadsthroughthecastle.data.model

data class User(var email: String,
                var password: String,
                var user: String,
                var confirmPassword: String ) {

    constructor(email: String, password: String) : this(email, password, "", "")

    companion object {
        const val TAG = "user"
        const val REMBEBER = "recordar_usuario"
        const val EMAIL = "email"
        const val USER = "user"
    }
}

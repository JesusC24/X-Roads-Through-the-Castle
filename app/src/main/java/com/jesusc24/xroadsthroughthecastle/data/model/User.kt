package com.jesusc24.xroadsthroughthecastle.data.model

data class User(var email: String,
                var password: String,
                var name: String,
                var image: String?,
                var confirmPassword: String = "",
                var token: String = ""){

    constructor(email: String, password: String) : this(email, password, "", "", "", "")

    companion object {
        const val TAG = "user"
    }

    override fun equals(other: Any?): Boolean {
        if(this === other) {
            return true
        }

        //Ver si son de la misma clase y por tanto a continuación se puede utilizar
        //"Unsafe" cast operator. Se puede utilizar el operador is
        //if (other is user)
        if(javaClass != other?.javaClass) {
            return false
        }

        other as User

        return email == other.email

    }
}

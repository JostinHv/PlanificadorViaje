package com.jostin.planificadorviaje.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.data.repository.LoginRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> get() = _loginResult

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch {
            val (role, name) = loginRepository.validateUser(email, password)
            if (name != null) {
                // Guarda la sesión del usuario
                val user = loginRepository.getUserByEmail(email)
                user.let { UserSessionManager.saveUser(context, it) }
            }
            _loginResult.value = role
        }
    }

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val user = User(
                id = System.currentTimeMillis().toString(),
                name = name,
                email = email,
                password = password,
                role = "usuario"
            )
            val isRegistered = loginRepository.registerUser(user)
            _registrationResult.value = isRegistered
        }
    }
}

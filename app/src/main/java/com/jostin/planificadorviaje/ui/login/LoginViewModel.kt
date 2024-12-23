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
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<String?>()
    val loginResult: LiveData<String?> get() = _loginResult

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser
    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch {
            val (role, name) = loginRepository.validateUser(email, password)
            if (name != null) {
                // Retrieve user details and save to session
                val user = loginRepository.getUserByEmail(email)
                if (user != null) {
                    UserSessionManager.saveUser(context, user)
                    _currentUser.value = user
                }
            } else {
                _currentUser.value = null
            }
            _loginResult.value = role
        }
    }


    fun registerUser(
        name: String,
        lastname: String,
        email: String,
        password: String,
        context: Context
    ) {
        viewModelScope.launch {
            val user = User(
                id = UUID.randomUUID().toString(),
                name = name,
                lastname = lastname,
                email = email,
                password = password,
                role = "Usuario"
            )
            val isRegistered = loginRepository.registerUser(user)
            if (isRegistered) {
                // Save the new user to the session
                UserSessionManager.saveUser(context, user)
                _currentUser.value = user
            }
            _registrationResult.value = isRegistered
        }
    }

    
}

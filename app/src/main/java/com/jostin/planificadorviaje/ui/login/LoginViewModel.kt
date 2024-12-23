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
import org.mindrot.jbcrypt.BCrypt
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
            val user = loginRepository.getUserByEmail(email)
            if (user != null && verifyPassword(password, user.password)) {
                UserSessionManager.saveUser(context, user)
                _currentUser.value = user
                _loginResult.value = user.role
            } else {
                _currentUser.value = null
                _loginResult.value = null
            }
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
            val hashedPassword = hashPassword(password) // Hashear la contraseña
            val user = User(
                id = UUID.randomUUID().toString(),
                name = name,
                lastname = lastname,
                email = email,
                password = hashedPassword, // Almacenar el hash
                role = "Usuario"
            )
            val isRegistered = loginRepository.registerUser(user)
            if (isRegistered) {
                UserSessionManager.saveUser(context, user)
                _currentUser.value = user
            }
            _registrationResult.value = isRegistered
        }
    }

    // Método para hashear la contraseña
    private fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    private fun verifyPassword(inputPassword: String, hashedPassword: String): Boolean {
        return try {
            if (hashedPassword.startsWith("$2a$") || hashedPassword.startsWith("$2b$")) {
                BCrypt.checkpw(inputPassword, hashedPassword)
            } else {
                false // El hash no es válido
            }
        } catch (e: IllegalArgumentException) {
            false // El hash es inválido
        }
    }


}

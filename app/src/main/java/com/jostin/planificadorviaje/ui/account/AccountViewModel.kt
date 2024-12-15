package com.jostin.planificadorviaje.ui.account

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.data.repository.UserRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser() {
        viewModelScope.launch {
            // Intenta obtener el usuario actual
            val currentUser = repository.getCurrentUser()
            if (currentUser != null) {
                _user.value = currentUser
            } else {
                // Si no hay usuario, crea un usuario vacío o muestra un error
                _user.value = User(name = "Usuario", email = "example@example.com")
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.updateUser(user)
            _user.value = user
        }
    }

    fun logout(context: Context) {
        viewModelScope.launch {
            UserSessionManager.clearUserSession(context) // Limpia la sesión en preferencias
        }
    }
}

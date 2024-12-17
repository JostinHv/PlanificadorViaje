package com.jostin.planificadorviaje.ui.account

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.data.model.User
import com.jostin.planificadorviaje.data.repository.UserRepository
import com.jostin.planificadorviaje.utils.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AccountViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(context: Context) {
        viewModelScope.launch {
            val currentUser = UserSessionManager.getCurrentUser()
            _user.value = currentUser ?: User(
                id = "",
                name = "Invitado",
                email = "guest@example.com",
                profilePicture = ""
            )
        }
    }

    fun updateUser(user: User, context: Context) {
        viewModelScope.launch {
            userRepository.updateUser(user) // Actualiza en el repositorio
            UserSessionManager.saveUser(context, user) // Actualiza la sesión activa
            _user.value = user // Actualiza el `LiveData` para reflejar los cambios en la UI
        }
    }


    fun logout(context: Context) {
        viewModelScope.launch {
            UserSessionManager.clearUserSession(context) // Limpia la sesión en preferencias
        }
    }
}

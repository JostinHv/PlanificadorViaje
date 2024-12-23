package com.jostin.planificadorviaje.ui.account

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jostin.planificadorviaje.utils.Result
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

    private val _updateResult = MutableLiveData<Result>()
    val updateResult: LiveData<Result> = _updateResult

    fun loadUser() {
        // Cargar el usuario actual desde el UserSessionManager
        _user.value = UserSessionManager.getCurrentUser()
    }

    fun updateUser(user: User, context: Context) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(user) // Actualiza en el repositorio
                UserSessionManager.saveUser(context, user) // Actualiza la sesión activa
                _user.value = user // Reflejar cambios en la UI
                _updateResult.value = Result(success = true)
            } catch (e: Exception) {
                _updateResult.value = Result(success = false, error = e.message)
            }
        }
    }

    fun logout(context: Context) {
        UserSessionManager.clearUserSession(context)
        _user.value = User("", "", "") // Restablece el estado de usuario
    }
}


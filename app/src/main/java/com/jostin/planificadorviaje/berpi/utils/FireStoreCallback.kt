package com.jostin.planificadorviaje.berpi.utils

interface FireStoreCallback<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}
package com.jostin.planificadorviaje.utils

interface FireStoreCallback<T> {
    fun onSuccess(data: T)
    fun onError(message: String)
}
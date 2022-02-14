package com.puneet.employeedirectoryapp.util

sealed class GenericResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val refresh: Boolean = false
) {
    class Success<T>(data: T, refresh: Boolean = false) :
        GenericResponse<T>(data, refresh = refresh)

    class Error<T>(message: String, data: T? = null, refresh: Boolean = false) :
        GenericResponse<T>(data, message, refresh)

    class Loading<T>(refresh: Boolean = false) : GenericResponse<T>(refresh = refresh)
}
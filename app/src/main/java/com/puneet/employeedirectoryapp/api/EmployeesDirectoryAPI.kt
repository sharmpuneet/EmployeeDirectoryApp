package com.puneet.employeedirectoryapp.api

import com.puneet.employeedirectoryapp.model.EmployeeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeesDirectoryAPI {

    @GET("sq-mobile-interview/{input}")
    suspend fun getEmployeesDirectory(@Path("input") input: String) : Response<EmployeeResponse>
}
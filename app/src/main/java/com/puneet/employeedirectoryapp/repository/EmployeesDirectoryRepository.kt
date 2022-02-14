package com.puneet.employeedirectoryapp.repository

import com.puneet.employeedirectoryapp.api.RetrofitInstance

class EmployeesDirectoryRepository {

    suspend fun getEmployeeDetails(input: String) = RetrofitInstance.EMPLOYEES_DIRECTORY_API.getEmployeesDirectory(input)
}
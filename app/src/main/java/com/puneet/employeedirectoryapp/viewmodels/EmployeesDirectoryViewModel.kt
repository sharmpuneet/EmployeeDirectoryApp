package com.puneet.employeedirectoryapp.viewmodels

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puneet.employeedirectoryapp.model.Employee
import com.puneet.employeedirectoryapp.model.EmployeeResponse
import com.puneet.employeedirectoryapp.repository.EmployeesDirectoryRepository
import com.puneet.employeedirectoryapp.util.GenericResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.*

class EmployeesDirectoryViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val repository: EmployeesDirectoryRepository
) : ViewModel(), LifecycleObserver {
    val employeeDetailsLiveData: MutableLiveData<GenericResponse<EmployeeResponse>> =
        MutableLiveData()
    private var employeeDetailsResponse: EmployeeResponse? = null
    private lateinit var url: String

    fun getEmployeesDirectory(refresh: Boolean = false, url: String) =
        viewModelScope.launch(dispatcher) {
            this@EmployeesDirectoryViewModel.url = url
            callEmployeeDirectoryAPI(refresh)
        }

    private suspend fun callEmployeeDirectoryAPI(refresh: Boolean) {
        employeeDetailsLiveData.postValue(GenericResponse.Loading(refresh = refresh))
        try {
            val response = repository.getEmployeeDetails(url)
            employeeDetailsLiveData.postValue(
                handleEmployeeDetailsResponse(
                    response,
                    refresh = refresh
                )
            )
        } catch (t: Throwable) {
            val message = when (t) {
                is IOException -> "Network Issue"
                else -> "Unknown Error is found"
            }
            employeeDetailsLiveData.postValue(GenericResponse.Error(message, refresh = refresh))
        }
    }

    fun handleEmployeeDetailsResponse(
        response: Response<EmployeeResponse>,
        refresh: Boolean
    ): GenericResponse<EmployeeResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (employeeDetailsResponse == null) {
                    employeeDetailsResponse = resultResponse
                }
                if (employeeDetailsResponse?.employees?.isEmpty() == true) {
                    return GenericResponse.Error(
                        "Empty Response", refresh = refresh
                    )
                }
                // Sort the list by names
                sortListByNames(employeeDetailsResponse?.employees)
                return GenericResponse.Success(
                    employeeDetailsResponse ?: resultResponse,
                    refresh = refresh
                )
            }
        }
        return GenericResponse.Error(response.message(), refresh = refresh)
    }

    private fun sortListByNames(employees: MutableList<Employee>?) {
        employees?.sortWith { employeeOne, employeeTwo -> employeeOne.fullName.compareTo(employeeTwo.fullName) }
    }
}
package com.puneet.employeedirectoryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puneet.employeedirectoryapp.repository.EmployeesDirectoryRepository
import kotlinx.coroutines.Dispatchers

class EmployeesDirectoryViewModelProviderFactory(
    private val
    employeesDirectoryRepository: EmployeesDirectoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.cast(
            EmployeesDirectoryViewModel(
                Dispatchers.Main,
                employeesDirectoryRepository
            )
        ) as T
    }
}
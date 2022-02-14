package com.puneet.employeedirectoryapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeResponse(
    val employees: MutableList<Employee>
) : Parcelable
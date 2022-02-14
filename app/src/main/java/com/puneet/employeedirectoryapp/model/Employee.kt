package com.puneet.employeedirectoryapp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.CONTRACTOR
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.FULL_TIME
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.PART_TIME
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee(
    @SerializedName("uuid")
    @Expose
    val uuid: String,
    @SerializedName("full_name")
    @Expose
    val fullName: String,
    @SerializedName("phone_number")
    @Expose
    val phoneNumber: String,
    @SerializedName("email_address")
    @Expose
    val emailAddress: String,
    @SerializedName("biography")
    @Expose
    val biography: String,
    @SerializedName("photo_url_small")
    @Expose
    val photoUrlSmall: String,
    @SerializedName("photo_url_large")
    @Expose
    val photoUrlLarge: String,
    @SerializedName("team")
    @Expose
    val team: String,
    @SerializedName("employee_type")
    @Expose
    var employeeType: String
) : Parcelable {
    fun getEmployeeTypeValue(): String = when (employeeType) {
        FULL_TIME -> "Full Time"
        PART_TIME -> "Part Time"
        CONTRACTOR -> "Contractor"
        else -> "New Member"
    }
}
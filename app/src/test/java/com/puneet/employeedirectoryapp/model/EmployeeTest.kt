package com.puneet.employeedirectoryapp.model

import com.puneet.employeedirectoryapp.util.Constants
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.CONTRACTOR
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.FULL_TIME
import com.puneet.employeedirectoryapp.util.Constants.EmployeeType.Companion.PART_TIME
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EmployeeTest {

    @Mock
    private lateinit var employee: Employee

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        employee = Mockito.mock(Employee::class.java)
    }

    @Test
    fun getEmployeeTypeValueForFullTime() {
        Mockito.`when`(employee.getEmployeeTypeValue())
            .thenReturn(getEmployee(FULL_TIME).getEmployeeTypeValue())
        val actualValue = employee.getEmployeeTypeValue()
        Assert.assertNotNull(employee)
        Assert.assertEquals("Full Time", actualValue)
    }

    @Test
    fun getEmployeeTypeValueForPartTime() {
        Mockito.`when`(employee.getEmployeeTypeValue())
            .thenReturn(getEmployee(PART_TIME).getEmployeeTypeValue())
        val actualValue = employee.getEmployeeTypeValue()
        Assert.assertNotNull(employee)
        Assert.assertEquals("Part Time", actualValue)
    }

    @Test
    fun getEmployeeTypeValueForNone() {
        Mockito.`when`(employee.getEmployeeTypeValue())
            .thenReturn(getEmployee("").getEmployeeTypeValue())
        val actualValue = employee.getEmployeeTypeValue()
        Assert.assertNotNull(employee)
        Assert.assertEquals("New Member", actualValue)
    }

    @Test
    fun getEmployeeTypeValueForContractor() {
        Mockito.`when`(employee.getEmployeeTypeValue())
            .thenReturn(getEmployee(CONTRACTOR).getEmployeeTypeValue())
        val actualValue = employee.getEmployeeTypeValue()
        Assert.assertNotNull(employee)
        Assert.assertEquals("Contractor", actualValue)
    }

    private fun getEmployee(employeeType: String): Employee {
        return Employee(
            "1232",
            "Test Name",
            "5423218374",
            "test@email.com",
            "Test Biography",
            "https://www.phtos.com/small-test-photo",
            "https://www.phtos.com/large-test-photo",
            "MobileApps",
            employeeType
        )
    }
}
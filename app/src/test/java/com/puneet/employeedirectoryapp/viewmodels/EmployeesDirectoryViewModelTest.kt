package com.puneet.employeedirectoryapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.puneet.employeedirectoryapp.model.Employee
import com.puneet.employeedirectoryapp.model.EmployeeResponse
import com.puneet.employeedirectoryapp.repository.EmployeesDirectoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class EmployeesDirectoryViewModelTest {

    private lateinit var viewModel: EmployeesDirectoryViewModel

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var employeesDirectoryRepository: EmployeesDirectoryRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetEmployeesDirectory_Success() = testDispatcher.runBlockingTest {
        viewModel = EmployeesDirectoryViewModel(testDispatcher, employeesDirectoryRepository)
        val retroResponse = getEmployeeRetrofitResponse(true)
        Mockito.`when`(employeesDirectoryRepository.getEmployeeDetails(anyString()))
            .thenReturn(retroResponse)
        viewModel.getEmployeesDirectory(url = "employees.json")
        Mockito.verify(employeesDirectoryRepository, Mockito.times(1))
            .getEmployeeDetails("employees.json")
        Assert.assertEquals(1, viewModel.employeeDetailsLiveData.value?.data?.employees?.size)
    }

    @Test
    fun testGetEmployeesDirectory_Error() = testDispatcher.runBlockingTest {
        viewModel = EmployeesDirectoryViewModel(testDispatcher, employeesDirectoryRepository)
        val retroResponse = getEmployeeRetrofitResponse(false)
        Mockito.`when`(employeesDirectoryRepository.getEmployeeDetails(anyString()))
            .thenReturn(retroResponse)
        viewModel.getEmployeesDirectory(url = "employees_malformed.json")
        Mockito.verify(employeesDirectoryRepository, Mockito.times(1))
            .getEmployeeDetails("employees_malformed.json")
        Assert.assertNull(viewModel.employeeDetailsLiveData.value?.data)
    }

    @Test
    fun testHandleEmployeeDetailsResponse_Success() {
        viewModel = EmployeesDirectoryViewModel(testDispatcher, employeesDirectoryRepository)
        val retroResponse = getEmployeeRetrofitResponse(true)
        val expectedOutput = viewModel.handleEmployeeDetailsResponse(retroResponse, false)

        Assert.assertEquals("Full_Time", expectedOutput.data?.employees?.get(0)?.employeeType)
        Assert.assertEquals("Test Name", expectedOutput.data?.employees?.get(0)?.fullName)
        Assert.assertEquals("MobileApps", expectedOutput.data?.employees?.get(0)?.team)
    }

    @Test
    fun testHandleEmployeeDetailsResponse_Failure() {
        viewModel = EmployeesDirectoryViewModel(testDispatcher, employeesDirectoryRepository)
        val retroResponse = getEmployeeRetrofitResponse(false)
        val expectedOutput = viewModel.handleEmployeeDetailsResponse(retroResponse, false)

        Assert.assertEquals(null, expectedOutput.data?.employees)
    }

    // Provides a response
    private fun getEmployeeRetrofitResponse(success: Boolean): Response<EmployeeResponse> {
        val employeeData = Employee(
            "1232",
            "Test Name",
            "5423218374",
            "test@email.com",
            "Test Biography",
            "https://www.phtos.com/small-test-photo",
            "https://www.phtos.com/large-test-photo",
            "MobileApps",
            "Full_Time"
        )
        val list = mutableListOf<Employee>()
        list.add(employeeData)
        val response = EmployeeResponse(list)
        return if (success) Response.success(response) else Response.error(400, ErrorResponseBody())
    }

    // Added to provide an error body
    private class ErrorResponseBody : ResponseBody() {
        override fun contentLength(): Long {
            return 100
        }

        override fun contentType(): MediaType? {
            return null
        }

        override fun source(): BufferedSource {
            TODO("Not yet implemented")
        }

    }
}
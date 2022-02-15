package com.puneet.employeedirectoryapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puneet.employeedirectoryapp.BuildConfig
import com.puneet.employeedirectoryapp.adapter.EmployeesDirectoryAdapter
import com.puneet.employeedirectoryapp.databinding.FragmentEmployeesDirectoryBinding
import com.puneet.employeedirectoryapp.repository.EmployeesDirectoryRepository
import com.puneet.employeedirectoryapp.util.GenericResponse
import com.puneet.employeedirectoryapp.viewmodels.EmployeesDirectoryViewModel
import com.puneet.employeedirectoryapp.viewmodels.EmployeesDirectoryViewModelProviderFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EmployeesDirectoryFragment : Fragment() {

    private lateinit var employeesDirectoryViewModel: EmployeesDirectoryViewModel
    private lateinit var employeesDirectoryAdapter: EmployeesDirectoryAdapter

    private var _binding: FragmentEmployeesDirectoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmployeesDirectoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val employeeDetailsRepository = EmployeesDirectoryRepository()
        val viewModelProviderFactory =
            EmployeesDirectoryViewModelProviderFactory(employeeDetailsRepository)
        employeesDirectoryViewModel =
            ViewModelProvider(
                this,
                viewModelProviderFactory
            )[EmployeesDirectoryViewModel::class.java]
        employeesDirectoryViewModel.getEmployeesDirectory(url = BuildConfig.EMPLOYEES_DIRECTORY_API)
        setupRecyclerView()
        setupObserver()
        setSwipeToRefreshListener()
    }

    private fun setupRecyclerView() {
        employeesDirectoryAdapter = EmployeesDirectoryAdapter()
        binding?.employeeListView?.apply {
            adapter = employeesDirectoryAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupObserver() {
        employeesDirectoryViewModel.employeeDetailsLiveData.observe(
            viewLifecycleOwner,
            { response ->
                when (response) {
                    is GenericResponse.Success -> {
                        handleLoading(refresh = response.refresh)
                        response.data?.let { employeesDetailsResponse ->
                            employeesDirectoryAdapter.differ.submitList(employeesDetailsResponse.employees.toList())
                        }
                    }
                    is GenericResponse.Error -> {
                        handleLoading(refresh = response.refresh)
                        showEmptyState()
                    }
                    is GenericResponse.Loading -> {
                        if (response.refresh) binding?.swipeRefreshLayout?.isRefreshing =
                            false else showProgressBar()
                    }
                }
            })
    }

    private fun showEmptyState() {
        binding?.swipeRefreshLayout?.visibility = GONE
        binding?.textViewEmptyStates?.visibility = VISIBLE
    }

    private fun handleLoading(refresh: Boolean) {
        if (refresh) binding?.swipeRefreshLayout?.isRefreshing = false else hideProgressBar()
    }

    private fun setSwipeToRefreshListener() {
        binding?.swipeRefreshLayout?.setOnRefreshListener {
            employeesDirectoryViewModel.getEmployeesDirectory(
                true,
                url = BuildConfig.EMPLOYEES_DIRECTORY_API
            )
        }
    }

    private fun hideProgressBar() {
        binding?.paginationProgressBar?.visibility = INVISIBLE
    }

    private fun showProgressBar() {
        binding?.paginationProgressBar?.visibility = VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
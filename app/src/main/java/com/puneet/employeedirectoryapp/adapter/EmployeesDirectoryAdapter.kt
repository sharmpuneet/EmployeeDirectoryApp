package com.puneet.employeedirectoryapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.puneet.employeedirectoryapp.R
import com.puneet.employeedirectoryapp.databinding.ItemEmployeeDetailsBinding
import com.puneet.employeedirectoryapp.model.Employee

class EmployeesDirectoryAdapter :
    RecyclerView.Adapter<EmployeesDirectoryAdapter.EmployeesViewHolder>() {

    // View Holder Class
    inner class EmployeesViewHolder(val binding: ItemEmployeeDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Implement Differ Call back using diff util
    private val differCallBack = object : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.uuid == newItem.uuid
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesViewHolder {
        val binding = ItemEmployeeDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EmployeesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeesViewHolder, position: Int) {
        val employee = differ.currentList[position]

        holder.apply {
            Glide.with(holder.itemView.context)
                .load(employee.photoUrlLarge)
                .placeholder(R.drawable.place_holder_image)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.imageView)
            binding.textViewName.text = employee.fullName
            binding.textViewTeam.text = employee.team
            binding.textViewType.text = employee.getEmployeeTypeValue()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
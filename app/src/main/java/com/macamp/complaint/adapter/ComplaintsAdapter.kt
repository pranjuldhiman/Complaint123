package com.macamp.complaint.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.LayoutItemBinding
import com.macamp.complaint.ui.activities.PendingActivity
import com.macamp.complaint.utils.startActivity

class ComplaintsAdapter(
    private val list: ArrayList<Complaints>,
    private val isPending: Boolean,
    private val context: Context,
    private val onItemClick: (ArrayList<Complaints>) -> Unit
) :
    RecyclerView.Adapter<ComplaintsAdapter.RecyclerHolder>() {
    lateinit var binding: LayoutItemBinding
    private val selectedList: ArrayList<Complaints> = ArrayList()

    //    lateinit var binding: ItemLayoutBinding

    class RecyclerHolder(var binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            complaints: Complaints,
            isPending: Boolean,
            context: Context,
            onItemClick: (ArrayList<Complaints>) -> Unit,
            selectedList: ArrayList<Complaints>,
        ) {
            binding.apply {
                val complaintNo = "Complaint Id:- ${complaints.id}"
                tvId.text = complaintNo
                tvName.text = "From - ${complaints.name}"
                tvComplaint.text = complaints.complaint
                tvStatus.text = complaints.status
            }
            binding.root.setOnClickListener {
                context.startActivity<PendingActivity> {
                    putExtra("isPending", isPending)
                    putExtra("data", complaints)
                }
            }
            binding.checkBox.setOnCheckedChangeListener { _, b ->
                if (binding.checkBox.isChecked) {
                    complaints.isSelected = true
                    selectedList.add(complaints)
                    onItemClick(selectedList)
                } else {
                    complaints.isSelected = false
                    selectedList.remove(complaints)
                    onItemClick(selectedList)
                }
            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHolder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(list[position], isPending, context, onItemClick, selectedList)
    }

    override fun getItemCount() = list.size
}
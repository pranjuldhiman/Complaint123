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
) :
    RecyclerView.Adapter<ComplaintsAdapter.RecyclerHolder>() {
    lateinit var binding: LayoutItemBinding
//    lateinit var binding: ItemLayoutBinding

    class RecyclerHolder(var binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            complaints: Complaints,
            isPending: Boolean,
            context: Context,
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

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerHolder {
        binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(list[position], isPending, context)
    }

    override fun getItemCount() = list.size
}
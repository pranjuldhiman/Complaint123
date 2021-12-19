package com.macamp.complaint.ui.fragments.allComplaints

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.macamp.complaint.adapter.ComplaintsAdapter
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.FragmentComplaintsBinding
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.ui.fragments.viewModel.ComplaintsViewModel
import com.macamp.complaint.utils.getUserInfo
import com.macamp.complaint.utils.sendMessage

class AllComplaintsFragment : BaseFragment() {
    lateinit var binding: FragmentComplaintsBinding
    private val viewModel by viewModels<ComplaintsViewModel>()
    private val list = ArrayList<Complaints>()
    private var complaintsAdapter: ComplaintsAdapter? = null
    private var selectedList = ArrayList<Complaints>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentComplaintsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complaintsAdapter =
            ComplaintsAdapter(list, context = requireActivity(), isPending = false) { list ->
                Log.e("TAG", "onSelectedItems: ${list.size}")
            }

        binding.recyclerView.apply { adapter = complaintsAdapter }
        binding.shareBtn.setOnClickListener {
            var shareMessageOnWhatsApp = ""
            selectedList.forEach { complaints ->

                shareMessageOnWhatsApp =
                    shareMessageOnWhatsApp + "Complaint : ${complaints.complaint}\n" +
                            "Complaint ID\t: ${complaints.id}\n" +
                            "Name\t: ${complaints.name}\n" +
                            "Mobile : ${complaints.mobile}\n" +
                            "Status\t: ${complaints.status}\n" +
                            "Address\t : ${complaints.address}\n" +
                            "Parshad\t : ${complaints.parshad}\n" +
                            "Department\t: ${complaints.department}\n" +
                            "Ward No : ${complaints.wardNo}\n" +
                            "-----------------------------\n"
            }
            requireActivity().sendMessage(shareMessageOnWhatsApp)
        }

        // Refresh function for the layout
        binding.swipeRefreshLayout.setOnRefreshListener{

            getAllComplaintsData()

            // This line is important as it explicitly refreshes only once
            // If "true" it implicitly refreshes forever
            binding.swipeRefreshLayout.isRefreshing = false
        }
        getAllComplaintsData()
    }

    private fun getAllComplaintsData() {
        val user = getUserInfo()
        viewModel.getAllComplaints(user?.id.toString()).observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    showProgress()
                }
                Status.SUCCESS -> {
                    hideProgress()
                    binding.noDataImage.visibility =
                        if (it.data?.body()?.isEmpty() == true) View.VISIBLE else View.GONE
                    binding.shareBtn.visibility =
                        if (it.data?.body()?.isNotEmpty() == true) View.VISIBLE else View.GONE

                    it.data?.let { response ->
                        list.clear()
                        response.body()?.let { it1 -> list.addAll(it1) }
                        complaintsAdapter?.notifyDataSetChanged()
                    }
                }
                Status.ERROR -> {
                    hideProgress()
                }
            }
        }
    }
}
package com.macamp.complaint.ui.fragments.allComplaints

import android.os.Bundle
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

class AllComplaintsFragment : BaseFragment() {
    lateinit var binding: FragmentComplaintsBinding
    private val viewModel by viewModels<ComplaintsViewModel>()
    private val list = ArrayList<Complaints>()
    private var complaintsAdapter: ComplaintsAdapter? = null
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
        complaintsAdapter = ComplaintsAdapter(list, context =  requireActivity(), isPending = false)
        binding.recyclerView.apply {
            adapter = complaintsAdapter
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
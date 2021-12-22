package com.macamp.complaint.ui.fragments.resolvedComplaints

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.macamp.complaint.adapter.ComplaintsAdapter
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.FragmentPendingBinding
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.ui.fragments.viewModel.ComplaintsViewModel
import com.macamp.complaint.utils.getUserInfo

class ResolvedFragment : BaseFragment() {
    private var selectedList = ArrayList<Complaints>()
    private val viewModel by viewModels<ComplaintsViewModel>()
    private val list = ArrayList<Complaints>()
    private var complaintsAdapter: ComplaintsAdapter? = null
    private lateinit var binding: FragmentPendingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPendingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complaintsAdapter =
            ComplaintsAdapter(list, isPending = false, context = requireActivity()) { list ->
                Log.e("TAG", "onSelectedItems: ${list.size}")
                selectedList = list
            }
        binding.recyclerView.apply {
            adapter = complaintsAdapter
        }

        getResolvedComplaints()
    }

    private fun getResolvedComplaints() {
        val user = getUserInfo()

        viewModel.getCompletedComplaints(user?.id.toString()).observe(viewLifecycleOwner) {
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
package com.macamp.complaint.ui.fragments.submittedComplaints

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.macamp.complaint.adapter.ComplaintsAdapter
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.FragmentSubmittedBinding
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.ui.fragments.viewModel.ComplaintsViewModel
import com.macamp.complaint.utils.getUserInfo


class SubmittedFragment : BaseFragment() {
    private val viewModel by viewModels<ComplaintsViewModel>()
    private val list = ArrayList<Complaints>()
    private var complaintsAdapter: ComplaintsAdapter? = null
    private lateinit var binding: FragmentSubmittedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSubmittedBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complaintsAdapter = ComplaintsAdapter(list, context = requireActivity(), isPending = false)
        binding.recyclerView.apply {
            adapter = complaintsAdapter
        }

        getSubmittedComplaints()
    }

    private fun getSubmittedComplaints() {
        val user = getUserInfo()

        viewModel.getSubmittedComplaints(user?.id.toString()).observe(viewLifecycleOwner) {
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
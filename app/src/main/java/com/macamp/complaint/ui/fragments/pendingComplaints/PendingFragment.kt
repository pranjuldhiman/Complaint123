package com.macamp.complaint.ui.fragments.pendingComplaints

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.macamp.complaint.adapter.ComplaintsAdapter
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.Complaints
import com.macamp.complaint.databinding.FragmentPendingBinding
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.ui.fragments.viewModel.ComplaintsViewModel
import com.macamp.complaint.utils.getUserInfo
import com.macamp.complaint.utils.sendMessage
import com.macamp.complaint.utils.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class PendingFragment : BaseFragment() {
    private val viewModel by viewModels<ComplaintsViewModel>()
    private val list = ArrayList<Complaints>()
    private var selectedList = ArrayList<Complaints>()
    private var complaintsAdapter: ComplaintsAdapter? = null
    private lateinit var binding: FragmentPendingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPendingBinding.inflate(layoutInflater, container, false)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mMessageReceiver,
             IntentFilter("custom-event-name")
        );
        return binding.root
    }

     override fun onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mMessageReceiver)
        super.onDestroy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        complaintsAdapter =
            ComplaintsAdapter(list, isPending = true, context = requireActivity()) { list ->
                selectedList = list
                Log.e("TAG", "onSelectedItems: ${list.size}")
            }
        binding.recyclerView.apply {
            adapter = complaintsAdapter
        }

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
            if (selectedList.size > 0) {
                requireActivity().sendMessage(shareMessageOnWhatsApp)

            } else {
                toast("Please select at least 1 complaint")
            }
        }
        binding.noDataImage.setOnClickListener {
            EventBus.getDefault().postSticky("true")
        }

        // Refresh function for the layout
        binding.swipeRefreshLayout.setOnRefreshListener {
            getPendingComplaintsData()
            // This line is important as it explicitly refreshes only once
            // If "true" it implicitly refreshes forever
            binding.swipeRefreshLayout.isRefreshing = false
        }
        getPendingComplaintsData()
    }



    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // Get extra data included in the Intent
            getPendingComplaintsData()

        }
    }

    private fun getPendingComplaintsData() {
        val user = getUserInfo()

        viewModel.getInProcessComplaints(user?.id.toString()).observe(viewLifecycleOwner) {
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
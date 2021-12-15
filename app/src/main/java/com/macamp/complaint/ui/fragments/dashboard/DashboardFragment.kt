package com.macamp.complaint.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.macamp.complaint.data.api.Status
import com.macamp.complaint.data.model.DashboardData
import com.macamp.complaint.databinding.FragmentDashboardBinding
import com.macamp.complaint.ui.fragments.BaseFragment
import com.macamp.complaint.utils.getUserInfo
import android.R.color
import android.graphics.Color
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class DashboardFragment : BaseFragment() {

    private val viewModel by viewModels<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    private fun getDashboardData() {
        val user = getUserInfo()
        viewModel.getDashboardData(user?.id.toString())
            .observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        hideProgress()

                        it.data?.let { resource ->
                            if (resource.isSuccessful) {
                                setData(resource.body())
                            }
                        }
                    }
                    Status.ERROR -> {
                        hideProgress()

                    }
                }
            }
    }

    private fun setData(data: DashboardData?) {
        binding.submittedComplaintTxt.text = data?.submitted.toString()
        binding.AllComplaintTxt.text = data?.all.toString()
        binding.completedComplaintsTxt.text = data?.done.toString()
        binding.pendingComplaintsTxt.text = data?.inProcess.toString()

        binding.yesterdayComplaintsTxt.text = data?.yesterdayAll.toString()
        binding.yesterdayPendingTxt.text = data?.yesterdayInProcess.toString()


        setChartData(binding.pieChart, data)
    }

    private fun setChartData(chart: PieChart, data: DashboardData?) {

        chart.setUsePercentValues(true)
        chart.isDrawHoleEnabled = false
        chart.animateY(1000, Easing.EaseInOutQuad);
        chart.description.isEnabled = false
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(data?.inProcess?.toFloat() ?: 0F, "In Process"))
        entries.add(PieEntry(data?.done?.toFloat() ?: 0F, "Completed"))
        entries.add(PieEntry(data?.returnX?.toFloat() ?: 0F, "Return"))
        entries.add(PieEntry(data?.submitted?.toFloat() ?: 0F, "Submitted"))
        val colors: ArrayList<Int> = ArrayList()
        colors.add(0xFFDF4759.toInt())
        colors.add(0xFF42BA96.toInt())
        colors.add(0xFF167BFF.toInt())
        colors.add(0xFF9C8245.toInt())
        val pieDataSet = PieDataSet(entries, ".")
        pieDataSet.colors = colors

        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(true)
        pieData.setValueFormatter(PercentFormatter(chart))
        pieData.setValueTextSize(15F)
        pieData.setValueTextColor(Color.BLACK)

        chart.data = pieData

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDashboardData()

        onClickEvents()
    }

    private fun onClickEvents() {
        binding.allComplaintsBtn.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToComplaintsFragment()
            findNavController().navigate(action)
        }
        binding.completedBtn.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToCompletedFragment()
            findNavController().navigate(action)
        }
        binding.submittedBtn.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToSubmittedFragment()
            findNavController().navigate(action)
        }
        binding.pendingBtn.setOnClickListener {
            val action = DashboardFragmentDirections.actionDashboardFragmentToPendingFragment()
            findNavController().navigate(action)
        }
    }
}
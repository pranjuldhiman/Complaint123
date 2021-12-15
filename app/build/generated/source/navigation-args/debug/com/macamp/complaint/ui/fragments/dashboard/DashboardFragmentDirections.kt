package com.macamp.complaint.ui.fragments.dashboard

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.macamp.complaint.NavGraphDirections
import com.macamp.complaint.R

public class DashboardFragmentDirections private constructor() {
  public companion object {
    public fun actionDashboardFragmentToComplaintsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_complaintsFragment)

    public fun actionDashboardFragmentToPendingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_pendingFragment)

    public fun actionDashboardFragmentToCompletedFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_completedFragment)

    public fun actionDashboardFragmentToSubmittedFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment_to_submittedFragment)

    public fun actionDashboardFragment(): NavDirections =
        NavGraphDirections.actionDashboardFragment()

    public fun actionComplaintsFragment(): NavDirections =
        NavGraphDirections.actionComplaintsFragment()

    public fun actionReturnFragment(): NavDirections = NavGraphDirections.actionReturnFragment()

    public fun actionPendingFragment(): NavDirections = NavGraphDirections.actionPendingFragment()

    public fun actionSubmitFragment(): NavDirections = NavGraphDirections.actionSubmitFragment()

    public fun actionResolvedFragment(): NavDirections = NavGraphDirections.actionResolvedFragment()
  }
}

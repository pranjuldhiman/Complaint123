package com.macamp.complaint.ui.fragments.allComplaints

import androidx.navigation.NavDirections
import com.macamp.complaint.NavGraphDirections

public class AllComplaintsFragmentDirections private constructor() {
  public companion object {
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

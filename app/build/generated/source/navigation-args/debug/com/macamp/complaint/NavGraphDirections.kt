package com.macamp.complaint

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class NavGraphDirections private constructor() {
  public companion object {
    public fun actionDashboardFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_dashboardFragment)

    public fun actionComplaintsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_complaintsFragment)

    public fun actionReturnFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_returnFragment)

    public fun actionPendingFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_pendingFragment)

    public fun actionSubmitFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_submitFragment)

    public fun actionResolvedFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_resolvedFragment)
  }
}

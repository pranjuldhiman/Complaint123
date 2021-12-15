package com.macamp.complaint.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.macamp.complaint.BuildConfig
import com.macamp.complaint.R
import com.macamp.complaint.databinding.ActivityMainBinding
import com.macamp.complaint.utils.*
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.startDestination = R.id.dashboardFragment
        navController.graph = navGraph
        navigationMenu(navController)
        setUserData()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment -> {
                    binding.titleTxt.text = Constants.DASHBOARD
                }
                R.id.complaintsFragment -> {
                    binding.titleTxt.text = Constants.COMPLAINTS
                }
                R.id.completedFragment -> {
                    binding.titleTxt.text = Constants.COMPLETED
                }
                R.id.submittedFragment -> {
                    binding.titleTxt.text = Constants.SUBMITTED
                }
                R.id.pendingFragment -> {
                    binding.titleTxt.text = Constants.PENDING
                }
            }
        }
    }

    private fun setUserData() {
        val user = getUserInfo()
        user?.profilePhotoUrl?.let { binding.navView.userImage.loadImageWithoutShimmer(it) }
        binding.navView.usernameTxt.text = user?.name
        binding.navView.numberTxt.text = user?.phone
    }

    private fun navigationMenu(navController: NavController) {
        binding.ivMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.navView.navView.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.navView.dashboardBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_dashboardFragment)
        }
        binding.navView.allComplaintsBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_complaintsFragment)
        }
        binding.navView.pendingBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_pendingFragment)
        }
        binding.navView.returnBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_returnFragment)
        }
        binding.navView.submittedBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_submitFragment)
        }
        binding.navView.resolvedComplaintsBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_resolvedFragment)
        }
        binding.navView.logoutBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            Preferences.prefs?.clearAll()
            startActivity<LauncherActivity>()
            finishAffinity()
        }
    }
}
package com.macamp.complaint.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.macamp.complaint.BuildConfig
import com.macamp.complaint.R
import com.macamp.complaint.databinding.ActivityMainBinding
import com.macamp.complaint.utils.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private fun getDeviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Preferences.prefs?.saveValue(Constants.FCM_TOKEN, token)
            Log.e("TAG", "Fetching FCM registration token : $token")

        })
    }
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

        getDeviceToken()

        // Set the toolbar
//        setSupportActionBar(binding.activityMainToolbar)
//
//
//        // Close the soft keyboard when you open or close the Drawer
//        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
//            this,
//            binding.drawerLayout,
//            binding.activityMainToolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        ) {
//            override fun onDrawerClosed(drawerView: View) {
//                // Triggered once the drawer closes
//                super.onDrawerClosed(drawerView)
//                try {
//                    val inputMethodManager =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//                } catch (e: Exception) {
//                    e.stackTrace
//                }
//            }
//
//            override fun onDrawerOpened(drawerView: View) {
//                // Triggered once the drawer opens
//                super.onDrawerOpened(drawerView)
//                try {
//                    val inputMethodManager =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//                } catch (e: Exception) {
//                    e.stackTrace
//                }
//            }
//        }
//        binding.drawerLayout.addDrawerListener(toggle)
//
//        toggle.syncState()

        binding.drawerLayout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        })
        setUserData()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.dashboardFragment -> {
                    binding.titleTxt.text = Constants.DASHBOARD
                    setUi(binding.navView.dashboardBtn)
                }
                R.id.complaintsFragment -> {
                    binding.titleTxt.text = Constants.COMPLAINTS
                    setUi(binding.navView.allComplaintsBtn)

                }
                R.id.completedFragment -> {
                    binding.titleTxt.text = Constants.RESOLVED
                    setUi(binding.navView.resolvedComplaintsBtn)
                }
                R.id.resolvedFragment -> {
                    binding.titleTxt.text = Constants.RESOLVED
                    setUi(binding.navView.resolvedComplaintsBtn)
                }
                R.id.submittedFragment -> {
                    binding.titleTxt.text = Constants.SUBMITTED
                    setUi(binding.navView.submittedBtn)
                }
                R.id.returnFragment -> {
                    binding.titleTxt.text = Constants.RETURN
                    setUi(binding.navView.returnBtn)
                }
                R.id.pendingFragment -> {
                    binding.titleTxt.text = Constants.PENDING
                    setUi(binding.navView.pendingBtn)
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(isRefreshing: String?) {
//        if (isRefreshing == "true")
        toast(isRefreshing ?: "")
    }

    private fun setUi(textView: AppCompatTextView) {
        binding.navView.dashboardBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.navView.allComplaintsBtn.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )
        binding.navView.pendingBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.navView.returnBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.navView.submittedBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        binding.navView.resolvedComplaintsBtn.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.white
            )
        )

        textView.setBackgroundColor(ContextCompat.getColor(this, R.color.selectionColor))

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
        binding.navView.returnBtn.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            navController.navigate(R.id.action_returnFragment)
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
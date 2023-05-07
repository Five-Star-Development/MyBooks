package dev.five_star.mybooks

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val visibility = when(destination.id) {
                R.id.mainFragment,
                R.id.archivedFragment,
                R.id.addBookDialog -> {
                    View.VISIBLE
                }
                else -> View.GONE
            }
            if (visibility != navView.visibility) {
                TransitionManager.beginDelayedTransition(navView, Slide())
                navView.visibility = visibility
            }
        }
    }
}

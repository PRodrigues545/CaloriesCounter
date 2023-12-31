package ipca.projeto.caloriescounter

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ipca.projeto.caloriescounter.databinding.ActivityMainBinding
import ipca.projeto.caloriescounter.ui.home.CounterFragment
import ipca.projeto.caloriescounter.ui.home.HomeFragment

class  MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_counter
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Listen for item clicks on the BottomNavigationView
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    // Replace the current fragment with HomeFragment
                    Log.d("Navigation", "Home selected")
                    navController.navigate(R.id.navigation_home)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_counter -> {
                    // Replace the current fragment with CounterFragment
                    Log.d("Navigation", "Counter selected")
                    navController.navigate(R.id.action_navigation_home_to_counterFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                // Handle other menu items here if needed
                else -> false
            }
        }
    }
}
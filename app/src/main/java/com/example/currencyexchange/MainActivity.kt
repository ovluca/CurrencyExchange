package com.example.currencyexchange

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.currencyexchange.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            navController.graph
        )
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)


        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings_fragment -> {
                    navController.navigate(R.id.action_main_fragment_to_settings_fragment)
                    true
                }

                R.id.history_fragment -> {
                    navController.navigate(R.id.action_main_fragment_to_history_fragment)
                    true
                }
                else -> false
            }
        }

    }

}
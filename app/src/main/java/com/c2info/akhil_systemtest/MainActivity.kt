package com.c2info.akhil_systemtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.c2info.akhil_systemtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(
            this, navController
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp())
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun getViewModel(): MainViewModel {
        return mainViewModel
    }
}
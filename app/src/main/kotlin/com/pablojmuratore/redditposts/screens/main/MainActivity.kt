package com.pablojmuratore.redditposts.screens.main

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.pablojmuratore.redditposts.R
import com.pablojmuratore.redditposts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainContainer.setScrimColor(Color.TRANSPARENT)
        binding.mainContainer.drawerElevation = 0f
        binding.mainContainer.setDrawerShadow(R.drawable.nav_drawer_shadow, GravityCompat.START)

        if (!resources.getBoolean(R.bool.fixedDrawer)) {
            binding.mainContainer.openDrawer(GravityCompat.START)
        }
    }

    fun closeDrawer() {
        if (!resources.getBoolean(R.bool.fixedDrawer)) {
            binding.mainContainer.closeDrawer(GravityCompat.START)
        }
    }
}

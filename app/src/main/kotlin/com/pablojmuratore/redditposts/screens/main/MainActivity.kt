package com.pablojmuratore.redditposts.screens.main

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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

    override fun onResume() {
        super.onResume()
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.mainContainer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.START)
        } else {
            binding.mainContainer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START)
        }
    }

    fun closeDrawer() {
        if (!resources.getBoolean(R.bool.fixedDrawer)) {
            binding.mainContainer.closeDrawer(GravityCompat.START)
        }
    }
}

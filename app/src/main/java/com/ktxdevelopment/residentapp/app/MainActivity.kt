package com.ktxdevelopment.residentapp.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ktxdevelopment.residentapp.fragments.home.FragmentHome
import com.ktxdevelopment.residentapp.R
import com.ktxdevelopment.residentapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentHome: FragmentHome
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        fragmentHome = FragmentHome.newInstance()

        supportFragmentManager.beginTransaction().add(R.id.navHost, fragmentHome).commit()

    }
}
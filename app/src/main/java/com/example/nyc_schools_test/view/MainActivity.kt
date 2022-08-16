package com.example.nyc_schools_test.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.nyc_schools_test.databinding.ActivityMainBinding
import com.example.nyc_schools_test.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ViewModel by viewModels()
    private lateinit var bindingMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition() {
                viewModel.isLoading.value
            }
        }
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

    }


}




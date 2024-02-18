package com.lovejeet.geniusgrove.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    var binding : ActivitySplashBinding ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        animateZooMOut()
    }

    private fun animateZooMOut()
    {
        binding?.splashImage?.animate()
            ?.scaleX(0.5f)
            ?.scaleY(0.5f)
            ?.setDuration(1000)
            ?.withEndAction {
                animateZooMIn()
            }
            ?.start()
            }


    private fun animateZooMIn()
    {
        binding?.splashImage?.animate()
            ?.scaleX(100f)
            ?.scaleY(100f)
            ?.setDuration(500)
            ?.withEndAction {
              startNewActivity()
            }
            ?.start()
    }

    private  fun startNewActivity()
    {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

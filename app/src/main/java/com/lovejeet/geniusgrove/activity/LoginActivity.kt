package com.lovejeet.geniusgrove.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.databinding.ActivityLoginBinding
import com.lovejeet.geniusgrove.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    var binding : ActivityLoginBinding ?= null
    var navController : NavController ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val auth = Firebase.auth

        navController = findNavController(R.id.navController2)

        if(auth.currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}
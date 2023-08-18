package com.example.definexcase.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.definexcase.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object{
        const val BUNDLE_TOKEN = "token"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
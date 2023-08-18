package com.example.definexcase.presentation.ui

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Shader.TileMode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.definexcase.R
import com.example.definexcase.base.ViewState
import com.example.definexcase.data.dto.LoginRequestDto
import com.example.definexcase.databinding.FragmentLoginBinding
import com.example.definexcase.presentation.viewmodel.LoginViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        addListener()
        addObservers()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textShader: Shader = LinearGradient(
            0f,
            0f,
            binding.defineX.paint.measureText(binding.defineX.text.toString()),
            0f,

            intArrayOf(
                ContextCompat.getColor(requireContext(), R.color.pastelBlue),
                ContextCompat.getColor(requireContext(), R.color.darkBlue),
                ContextCompat.getColor(requireContext(), R.color.darkBlue),
            ),
            floatArrayOf(0f, 0.3f, 1f),
            TileMode.CLAMP
        )
        binding.defineX.paint.shader = textShader
    }

    private fun addListener(){
        binding.loginBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("login_button", "custom_button")
            firebaseAnalytics.logEvent("custom_button_clicked", bundle)
            viewModel.login(
                LoginRequestDto(
                    email = binding.emailText.text.toString(),
                    password = binding.passwordText.text.toString()
                )
            )
        }

        binding.forgotBtn.setOnClickListener {
            Toast.makeText(requireContext(), R.string.soon, Toast.LENGTH_SHORT).show()
        }
        binding.facebookBtn.setOnClickListener {
            Toast.makeText(requireContext(), R.string.soon, Toast.LENGTH_SHORT).show()
        }
        binding.twitterBtn.setOnClickListener {
            Toast.makeText(requireContext(), R.string.soon, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addObservers(){
        lifecycleScope.launch {
            viewModel.loginState.collect {
                when (it) {
                    is ViewState.Success -> {
                        startActivity(Intent(requireContext(), DiscoverActivity::class.java).apply {
                            putExtras(
                                bundleOf(LoginActivity.BUNDLE_TOKEN to it.data.token)
                            )
                        })
                    }
                    else -> {}
                }
            }
        }
    }
}
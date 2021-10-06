package com.example.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.sample.databinding.ActivityLoginBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        setContentView(binding.root)
        nitListeners()
        collectFlow()
    }

    @ExperimentalCoroutinesApi
    private fun initListeners() {
        var x=1/0
        binding.userName.addTextChangedListener { viewModel.setUserName(it.toString()) }
        binding.password.addTextChangedListener { viewModel.setPassword(it.toString()) }
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).putExtra(
                "username",
                binding.userName.text.toString()
            )
            startActivity(intent)
            finish()
        }
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.isSubmitEnabled.collect { validation ->
                if (!validation.username){
                    binding.usernameWarning.text = getString(R.string.username_warning)
                    binding.passwordWarning.text = ""
                }
                else if (!validation.password) {
                    binding.passwordWarning.text = getString(R.string.password_warning)
                    binding.usernameWarning.text = ""
                }
                else {
                    binding.usernameWarning.text= ""
                    binding.passwordWarning.text =""
                }
                binding.loginButton.isEnabled = validation.username && validation.password
            }
        }
    }

}
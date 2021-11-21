package com.example.nvesttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.nvesttask.databinding.FragmentLoginSignupBinding
import com.example.nvesttask.roomdatabase.UserEntity
import com.example.nvesttask.viewmodel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
class SignUpFragment : Fragment() {

    private lateinit var viewModel: ProductViewModel
    lateinit var fragmentLoginSignupBinding: FragmentLoginSignupBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentLoginSignupBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login_signup, container, false)

        initView()
        return fragmentLoginSignupBinding.root
    }

    private fun initView() {
        fragmentLoginSignupBinding.textInputLayout3.visibility = View.VISIBLE
        fragmentLoginSignupBinding.btnLogReg.text = resources.getString(R.string.signup)
        fragmentLoginSignupBinding.tvLogReg.text = resources.getString(R.string.login)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentLoginSignupBinding.btnLogReg.setOnClickListener {
            signUpUser()
        }
        fragmentLoginSignupBinding.tvLogReg.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        viewModel.isInserted.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireContext(), "Sign up successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
            }
            else Toast.makeText(requireContext(), "Sign up Failed", Toast.LENGTH_SHORT).show()
        })
    }

    private fun signUpUser() {
        val userName = fragmentLoginSignupBinding.tvName.text.toString()
        val password = fragmentLoginSignupBinding.tvPassword.text.toString()
        val confirmPassword = fragmentLoginSignupBinding.tvConfirmPassword.text.toString()

        when {
            userName.isEmpty() -> {
                fragmentLoginSignupBinding.tvName.error = "Enter User Name"
                fragmentLoginSignupBinding.tvName.requestFocus()
            }
            password.isEmpty() -> {
                fragmentLoginSignupBinding.tvPassword.error = "Enter Password"
                fragmentLoginSignupBinding.tvPassword.requestFocus()
            }
            confirmPassword.isEmpty() -> {
                fragmentLoginSignupBinding.tvConfirmPassword.error = "Enter Confirm Password"
                fragmentLoginSignupBinding.tvConfirmPassword.requestFocus()
            }
            else -> {
                if (userName.isNotEmpty()) {
                    if (password == confirmPassword) {
                        val entity = UserEntity(userName, password)
                        viewModel.signUpUser(entity)

                    } else {
                        Toast.makeText(context, "Password does not match", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }



    companion object {

    }
}
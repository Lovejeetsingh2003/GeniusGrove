package com.lovejeet.firebaseauthentication.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.lovejeet.geniusgrove.activity.LoginActivity
import com.lovejeet.geniusgrove.activity.MainActivity
import com.lovejeet.geniusgrove.databinding.FragmentForgetPasswordBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ForgetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgetPasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding : FragmentForgetPasswordBinding?= null
    var loginActivity : LoginActivity?= null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity
        auth = Firebase.auth
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetPasswordBinding.inflate(layoutInflater)
        return binding?.root
    }

    private fun resetPassword()
    {
        val email = binding?.etForgotPasswordEmail?.text.toString()
        if(validateForm(email))
        {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "A Confirmation Email Sent To You And Create New Password.", Toast.LENGTH_LONG)
                        .show()
                        loginActivity?.navController?.popBackStack()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Password Forget Unsuccessfully, Try Again Later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String): Boolean {
        return when{
            TextUtils.isEmpty(email)->{
                binding?.etForgotPasswordEmail?.error = "Enter Email"
                false
            }
            else ->{true}
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnForgetPsw?.setOnClickListener {
            resetPassword()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ForgetPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ForgetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
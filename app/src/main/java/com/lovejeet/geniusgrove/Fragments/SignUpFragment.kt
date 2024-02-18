package com.lovejeet.firebaseauthentication.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import com.lovejeet.geniusgrove.databinding.FragmentSignUpBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentSignUpBinding? = null
    var loginActivity: LoginActivity? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        auth = Firebase.auth
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.tvLogin?.setOnClickListener {
            loginActivity?.navController?.popBackStack()
        }

        binding?.btnSignUp?.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val name = binding?.etName?.text.toString()
        val email = binding?.etUsername?.text.toString()
        val password = binding?.etPassword?.text.toString()
        if (validateForm(name, email, password)) {
           auth.createUserWithEmailAndPassword(email,password)
               .addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       Toast.makeText(requireContext(), "Sign Up Successfully", Toast.LENGTH_LONG)
                           .show()
                       loginActivity?.navController?.popBackStack()
                   } else {
                       Toast.makeText(
                           requireContext(),
                           "Sign Up Unsuccessfully, Try Again Later",
                           Toast.LENGTH_LONG
                       ).show()
                   }
               }
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
     return when{
         TextUtils.isEmpty(name)->{
             binding?.etName?.error = "Enter Name"
             false
         }
         TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
             binding?.etUsername?.error = "Enter Valid Email Address"
             false
         }
         TextUtils.isEmpty(password)->{
             binding?.etPassword?.error = "Enter Password"
             false
         }
         else ->{true}
     }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
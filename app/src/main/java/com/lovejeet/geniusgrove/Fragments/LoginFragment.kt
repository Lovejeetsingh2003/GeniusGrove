package com.lovejeet.firebaseauthentication.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.activity.LoginActivity
import com.lovejeet.geniusgrove.activity.MainActivity
import com.lovejeet.geniusgrove.databinding.FragmentLoginBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var binding : FragmentLoginBinding?= null
    var loginActivity : LoginActivity?= null
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity
        // Initialize Firebase Auth
        auth = Firebase.auth

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun signInWithGoogle()
    {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result ->
        if(result.resultCode == Activity.RESULT_OK)
        {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>)
    {
        if(task.isSuccessful)
        {
            val account : GoogleSignInAccount ?= task.result
            if(account!=null)
            {
                updateUI(account)
            }
        }
        else
        {
        Toast.makeText(requireContext(),"Login Failed,Try Again Later.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount)
    {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Login Unsuccessfully, Try Again Later",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginActivity?.navController?.clearBackStack(R.id.getStartFragment2)
        binding?.tvSignUpNow?.setOnClickListener {
            loginActivity?.navController?.navigate(R.id.signUpFragment)
        }
        binding?.tvForgetPassword?.setOnClickListener {
            loginActivity?.navController?.navigate(R.id.forgetPasswordFragment)
        }
        binding?.btnLogin?.setOnClickListener {
            userLogin()
        }
        binding?.btnSignInWithGoogle?.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun userLogin()
    {
        val email = binding?.etUsername?.text.toString()
        val password = binding?.etPassword?.text.toString()
        if(validateForm(email,password))
        {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Login Successfully", Toast.LENGTH_LONG)
                            .show()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Login Unsuccessfully, Try Again Later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when{
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
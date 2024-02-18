package com.lovejeet.firebaseauthentication.Fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lovejeet.geniusgrove.R
import com.lovejeet.geniusgrove.activity.LoginActivity
import com.lovejeet.geniusgrove.databinding.FragmentGetstartBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GetSatrtFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetStartFragment : Fragment() {
    var binding : FragmentGetstartBinding?= null
    var loginActivity : LoginActivity?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivity = activity as LoginActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetstartBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnGetStart?.setOnClickListener {
            loginActivity?.navController?.navigate(R.id.loginFragment)
        }
    }

    companion object {

    }
}
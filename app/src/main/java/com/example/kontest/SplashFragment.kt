package com.example.kontest

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.kontest.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_splash,container,false)

        Handler().postDelayed({
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSitesFragment())
        },3500)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}
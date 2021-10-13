package com.example.kontest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.kontest.databinding.FragmentWebPageBinding

class WebPageFragment : Fragment() {

    private lateinit var binding : FragmentWebPageBinding
    private lateinit var args: WebPageFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_web_page,container,false)
        args = WebPageFragmentArgs.fromBundle(requireArguments())
        val url = args.urlSent
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

        findNavController().navigate(WebPageFragmentDirections.actionWebPageFragmentToContestFragment(args.nameSent,args.imageSent,args.responseSent))
        return binding.root
    }
}
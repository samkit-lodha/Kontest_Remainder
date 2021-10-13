package com.example.kontest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kontest.adapters.MyAdapterS
import com.example.kontest.databinding.FragmentSitesBinding
import com.example.kontest.differentObject.SitesInfo

class SitesFragment : Fragment() {

    private lateinit var binding: FragmentSitesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : MyAdapterS
    private var sitesList = listOf(
        SitesInfo("Codeforces",R.drawable.codeforcesone),
        SitesInfo("Codeforces::Gym",R.drawable.codeforcesone),
        SitesInfo("TopCoder", R.drawable.topcoder),
        SitesInfo("AtCoder", R.drawable.atcoder),
        SitesInfo("CodeChef", R.drawable.codechef),
        SitesInfo("Hacker Rank", R.drawable.hackerrank),
        SitesInfo("Hacker Earth", R.drawable.hackerearth),
        SitesInfo("Kickstart", R.drawable.google_logo),
        SitesInfo("Leetcode", R.drawable.leetcode),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sites,container,false)


        adapter = MyAdapterS(requireContext(),sitesList)
        recyclerView = binding.sitesRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }
}
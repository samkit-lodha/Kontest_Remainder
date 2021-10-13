package com.example.kontest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kontest.adapters.MyAdapterC
import com.example.kontest.databinding.FragmentContestBinding
import com.example.kontest.differentObject.ContestInfo
import com.example.kontest.differentObject.SitesServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestFragment : Fragment() {

    private lateinit var binding : FragmentContestBinding

    private lateinit var recyclerView1 : RecyclerView
    private lateinit var recyclerView2 : RecyclerView
    private lateinit var recyclerView3 : RecyclerView

    private lateinit var adapter1: MyAdapterC
    private lateinit var adapter2: MyAdapterC
    private lateinit var adapter3: MyAdapterC

    private var sitesName : String = "Codeforces"
    private var sitesImage : Int = R.drawable.codeforcesone
    private var requestCode : Int = 0

    private var contestList = mutableListOf<ContestInfo>()

    private lateinit var args: ContestFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_contest,container,false)

        args = ContestFragmentArgs.fromBundle(requireArguments())

        sitesName = args.sitesNameSent
        sitesImage = args.sitesImageSent
        requestCode = args.requestCodeSent

        adapter1 = MyAdapterC(requireContext(),sitesImage,sitesName)
        adapter2 = MyAdapterC(requireContext(),sitesImage,sitesName)
        adapter3 = MyAdapterC(requireContext(),sitesImage,sitesName)


        recyclerView1 = binding.onGoingRecyclerView
        recyclerView2 = binding.inHoursRecyclerView
        recyclerView3 = binding.upComingRecyclerView

        recyclerView1.adapter = adapter1
        recyclerView2.adapter = adapter2
        recyclerView3.adapter = adapter3

        recyclerView1.layoutManager = LinearLayoutManager(requireContext())
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())
        recyclerView3.layoutManager = LinearLayoutManager(requireContext())

        getContestList()

        binding.inHoursTextView.setOnClickListener {

            if(adapter2.cList.size == 0){
                Toast.makeText(requireContext(),"No contest in 24 hours!",Toast.LENGTH_LONG).show()
            }
            else{
                if(binding.inHoursRecyclerView.isVisible == true){
                    binding.inHoursRecyclerView.visibility = View.GONE
                }
                else{

                    binding.onGoingRecyclerView.visibility = View.GONE
                    binding.inHoursRecyclerView.visibility = View.VISIBLE
                    binding.upComingRecyclerView.visibility = View.GONE
                }
            }
        }


        binding.onGoingTextView.setOnClickListener {
            if(adapter1.cList.size == 0){
                Toast.makeText(requireContext(),"There is no contest running currently!",Toast.LENGTH_LONG).show()
            }
            else{
                if(binding.onGoingRecyclerView.isVisible == true){
                    binding.onGoingRecyclerView.visibility = View.GONE
                }
                else{
                    binding.onGoingRecyclerView.visibility = View.VISIBLE
                    binding.inHoursRecyclerView.visibility = View.GONE
                    binding.upComingRecyclerView.visibility = View.GONE
                }
            }
        }


        binding.upComingTextView.setOnClickListener {
            if(adapter3.cList.size == 0){
                Toast.makeText(requireContext(),"There is no record of upcoming contest.We will get it shortly!",Toast.LENGTH_LONG).show()
            }
            else{
                if(binding.upComingRecyclerView.isVisible == true){
                    binding.upComingRecyclerView.visibility = View.GONE
                }
                else{
                    binding.onGoingRecyclerView.visibility = View.GONE
                    binding.inHoursRecyclerView.visibility = View.GONE
                    binding.upComingRecyclerView.visibility = View.VISIBLE
                }
            }
        }

        return binding.root
    }



    private fun getContestList() {

        if(sitesName.equals("Codeforces")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getCf()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })
        }

        if(sitesName.equals("Codeforces::Gym")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getCfg()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })
        }

        if(sitesName.equals("TopCoder")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getTc()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("AtCoder")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getAc()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("CodeChef")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getCc()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("Hacker Rank")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getHr()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("Hacker Earth")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getHe()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("Kickstart")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getGks()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

        if(sitesName.equals("Leetcode")){
            val totalContest : Call<List<ContestInfo>> = SitesServices.sitesInterface.getLe()
            totalContest.enqueue(object: Callback<List<ContestInfo>>{
                override fun onResponse(call: Call<List<ContestInfo>>, response: Response<List<ContestInfo>>) {
                    if(response.isSuccessful){
                        val rContestList = response.body()
                        contestList.addAll(rContestList!!)
                        for(key in contestList.indices){
                            val temp = contestList[key]

                            if(temp.status.equals("CODING")){
                                adapter1.addContest(temp)
                            }
                            else if(temp.in_24_hours.equals("Yes")){
                                adapter2.addContest(temp)
                            }
                            else{
                                adapter3.addContest(temp)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ContestInfo>>, t: Throwable) {
                    Toast.makeText(requireContext(),sitesName + t.message.toString(),Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}
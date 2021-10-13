package com.example.kontest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kontest.R
import com.example.kontest.SitesFragmentDirections
import com.example.kontest.differentObject.SitesInfo

class MyAdapterS(val context: Context,val sitesL : List<SitesInfo>) : RecyclerView.Adapter<MyAdapterS.MyViewModelS>() {

    class MyViewModelS(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sitesText = itemView.findViewById<TextView>(R.id.sitesTextView)
        val sitesImage = itemView.findViewById<ImageView>(R.id.sitesImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModelS {
        return MyViewModelS(LayoutInflater.from(context).inflate(R.layout.sites_list_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewModelS, position: Int) {
        val temp = sitesL[position]
        holder.sitesText.setText(temp.sitesName)
        holder.sitesImage.setImageResource(temp.sitesImage)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(SitesFragmentDirections.actionSitesFragmentToContestFragment(temp.sitesName,temp.sitesImage,position))
        }
    }

    override fun getItemCount(): Int {
        return sitesL.size
    }
}
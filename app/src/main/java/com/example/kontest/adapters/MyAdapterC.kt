package com.example.kontest.adapters

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.kontest.ContestFragmentDirections
import com.example.kontest.R
import com.example.kontest.Receivers.AlarmReceiver
import com.example.kontest.differentObject.ContestInfo
import java.util.*

class MyAdapterC(val context : Context, val image : Int,val sitesName:String) : RecyclerView.Adapter<MyAdapterC.MyViewModelC>() {
    var cList = arrayListOf<ContestInfo>()
    private lateinit var cal : Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    class MyViewModelC(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val contextName = itemView.findViewById<TextView>(R.id.contestName)
        val contestStart = itemView.findViewById<TextView>(R.id.contestStart)
        val contestEnd = itemView.findViewById<TextView>(R.id.contestEnd)
        val goContest = itemView.findViewById<TextView>(R.id.gotocontestbutton)
        val alarmContest = itemView.findViewById<TextView>(R.id.setalarmbutton)
        val contestImage = itemView.findViewById<ImageView>(R.id.contestImageView)
        val contestDur = itemView.findViewById<TextView>(R.id.contestDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModelC {
        return MyViewModelC(LayoutInflater.from(context).inflate(R.layout.contest_list_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewModelC, position: Int) {
        val temp = cList[position]

        holder.contestImage.setImageResource(image)
        holder.contextName.setText(temp.name)
        holder.contestStart.setText(convertTime(temp.start_time))
        holder.contestEnd.setText(convertTime(temp.end_time))
        holder.contestDur.setText(millitotime(temp.duration))
        holder.goContest.setOnClickListener {
            it.findNavController().navigate(ContestFragmentDirections.actionContestFragmentToWebPageFragment(temp.url,sitesName,image,position))
        }

        if(temp.in_24_hours.equals("No") || temp.status.equals("CODING")){
            holder.alarmContest.visibility = View.GONE
        }

        holder.alarmContest.setOnClickListener {
            if(holder.alarmContest.text == "Set Alarm"){
                cal = Calendar.getInstance()
                val timePickerDialog = TimePickerDialog.OnTimeSetListener { timepicker, hourOfDay, minute ->
                    cal.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    cal.set(Calendar.MINUTE,minute)
                    cal.set(Calendar.SECOND,0)
                    cal.set(Calendar.MILLISECOND,0)

                    val builder = AlertDialog.Builder(context)
                    builder.setPositiveButton("Yes"){_,_ ->
                        holder.alarmContest.text = "Cancel Alarm"
                        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                        val i = Intent(context,AlarmReceiver::class.java)
                        i.putExtra("SitesName",sitesName)
                        i.putExtra("requestCode",position)

                        pendingIntent = PendingIntent.getBroadcast(context,position,i,PendingIntent.FLAG_UPDATE_CURRENT)

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP,cal.timeInMillis,pendingIntent)

                        Toast.makeText(context,"Alarm set successfully",Toast.LENGTH_LONG).show()
                    }
                    builder.setNegativeButton("No"){_,_ ->

                    }

                    builder.setTitle("Set Alarm")
                    builder.setMessage("Are you sure you want to set the alarm for $sitesName contest?")

                    builder.create().show()
                }

                TimePickerDialog(context,timePickerDialog,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true).show()

            }
            else{
                val builder = AlertDialog.Builder(context)
                builder.setPositiveButton("Yes"){_,_ ->
                    holder.alarmContest.text = "Set Alarm"
                    val i = Intent(context,AlarmReceiver::class.java)
                    i.putExtra("SitesName",sitesName)
                    i.putExtra("requestCode",position)

                    pendingIntent = PendingIntent.getBroadcast(context,position,i,PendingIntent.FLAG_UPDATE_CURRENT)

                    if(alarmManager == null){
                        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    }

                    alarmManager.cancel(pendingIntent)
                    Toast.makeText(context,"Cancelled alarm!",Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("No"){_,_ ->

                }

                builder.setTitle("Cancel Alarm")
                builder.setMessage("Are you sure you want to cancel the alarm for $sitesName contest?")

                builder.create().show()
            }
        }
    }
    override fun getItemCount(): Int {
        return cList.size
    }

    fun addContest(cl : ContestInfo){
        cList.add(cl)
        notifyDataSetChanged()
    }

    private fun convertTime(str : String) : String {
        var hours : Int = str.substring(11,13).toInt()
        var minutes : Int = str.substring(14,16).toInt()

        hours+=5
        minutes+=30

        if(minutes>59){
            minutes-=60
            hours+=1
        }

        if(hours>23){
            hours-=24
        }

        var res = ""
        if(hours<10){
            res+="0"
        }
        res+=hours.toString()
        res+=" : "
        if(minutes<10){
            res+="0"
        }
        res+=minutes.toString()
        res+=("  ( " + str.substring(0,10) + " )")
        return res
    }

    private fun millitotime(str : String) : String{
        var end = 0
        for( c in str){
            if(c == '.'){
                break
            }
            end++
        }

        val temp = str.substring(0,end).toInt()
        var res = ""

        if(temp/86400 !=0 ){
            res = res + (temp/86400).toString() + " Days "
        }
        if((temp % 86400)/3600 !=0 ){
            res = res + ((temp % 86400)/3600).toString() + " Hours "
        }
        if((temp % 3600)/60 !=0 ){
            res = res + ((temp % 3600)/60).toString() + " Minutes "
        }

        return res
    }
}
package com.example.get_current_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapler : RecyclerView.Adapter<UserAdapler.UserViewHolder>() {
    private var userList:ArrayList<UserModel> = ArrayList()
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(listener:onItemClickListener){
        mListener = listener
    }

    fun addItems(item:ArrayList<UserModel>){
        this.userList=item
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UserViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_item_timeline,parent,false)
        return UserViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bindView(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }



    class UserViewHolder(var view: View,listener: onItemClickListener):RecyclerView.ViewHolder(view){
        private var date = view.findViewById<TextView>(R.id.sdate)
        private var time = view.findViewById<TextView>(R.id.stime)
        private var latitude = view.findViewById<TextView>(R.id.slat)
        private var longitude = view.findViewById<TextView>(R.id.slong)

        fun bindView(user:UserModel){
            date.text=user.datetext
            time.text=user.timetext
            latitude.text=user.latitube
            longitude.text=user.longitube
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}






























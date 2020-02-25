package com.example.usermanagement.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.usermanagement.R
import com.example.usermanagement.UpdateUserActivity
import com.example.usermanagement.model.User
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter (var context: Context, var user: List<User>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var item: List<User>? = user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return item!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is UserViewHolder -> {
                holder.bind(item!!.get(position))
                holder.itemView.setOnClickListener {
                    holder.itemView.setOnClickListener {
                        val newIntent = Intent(context, UpdateUserActivity::class.java).apply {
                            putExtra("name", item!![position].name)
                        }
                        context.startActivity(newIntent)
                    }
                }
            }
        }
    }

    class UserViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){
        val nameUser: TextView = itemView.textViewNameUserItem

        fun bind(event:User){
            nameUser.setText(event.name)
        }

    }
}

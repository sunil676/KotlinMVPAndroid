package com.mobologicplus.kotlinmvpandroid.ui

import android.app.Fragment
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.mobologicplus.kotlinmvpandroid.R
import com.mobologicplus.kotlinmvpandroid.model.Friends

/**
 * Created by sunil on 5/31/2017.
 */
class FriendsAdapter(private val context: Context, private val friendsList: MutableList<Friends.User>, fragment: Fragment) : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    private val listener: FriendsAdapter.onItemClickListener

    init {
        this.listener = fragment as FriendsAdapter.onItemClickListener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup?, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false)
        return FriendsAdapter.FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder?, position: Int) {
        var friends = friendsList[position]
        holder!!.name!!.setText(friends.name)
        holder.email!!.setText(friends.email)
        holder.delete!!.setOnClickListener {
            friendsList.remove(friends)
            notifyItemRemoved(position)
            listener.itemRemoveClick(friends.id!!.toInt())
        }
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }

    class FriendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        @BindView(R.id.name)
        @JvmField var name: TextView? = null
        @BindView(R.id.email)
        @JvmField var email: TextView? = null
        @BindView(R.id.delete)
        @JvmField var delete: ImageView? = null
        init {
            ButterKnife.bind(this, itemView)
        }

    }

    interface onItemClickListener {
        fun itemRemoveClick(position: Int)
    }
}
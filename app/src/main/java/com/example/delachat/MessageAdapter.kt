package com.example.delachat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import android.widget.ImageView
import com.bumptech.glide.Glide

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            return ReceiveViewHolder(view)
        }else{
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent, false)
            return SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder is SentViewHolder) {
            holder.sentMessage.text = currentMessage.message
            Glide.with(context)
                .load(currentMessage.message)
                .placeholder(R.drawable.photo)
                .into(holder.sentImage)
        } else if (holder is ReceiveViewHolder) {
            holder.receveMessage.text = currentMessage.message
            Glide.with(context)
                .load(currentMessage.message)
                .placeholder(R.drawable.photo)
                .into(holder.receiveImage)
        } else if (holder is ImageViewHolder) {
            Glide.with(context)
                .load(currentMessage.message)
                .placeholder(R.drawable.photo)
                .into(holder.sentImageView)
            Glide.with(context)
                .load(currentMessage.message)
                .placeholder(R.drawable.photo)
                .into(holder.receiveImageView)
        }
    }


    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage: TextView = itemView.findViewById(R.id.txt_sent_message)
        val sentImage: ImageView = itemView.findViewById(R.id.imagesent)
    }


    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receveMessage = itemView.findViewById<TextView>(R.id.txt_receive_message)
        val receiveImage: ImageView = itemView.findViewById(R.id.imagereceive)
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sentImageView: ImageView = itemView.findViewById(R.id.imagesent)
        val receiveImageView: ImageView = itemView.findViewById(R.id.imagereceive)
    }

}
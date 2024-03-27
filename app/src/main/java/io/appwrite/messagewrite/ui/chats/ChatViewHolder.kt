package io.appwrite.messagewrite.ui.chats

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import io.appwrite.messagewrite.R
import io.appwrite.messagewrite.models.views.Chat
import java.text.DateFormat
import java.util.Date


class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(chat: Chat) {
        val profileImage = itemView.findViewById<ImageView>(R.id.profile_image)
        val username = itemView.findViewById<TextView>(R.id.username)
        val lastMessage = itemView.findViewById<TextView>(R.id.message)
        val lastMessageTime = itemView.findViewById<TextView>(R.id.time)

        Glide
            .with(itemView.context)
            .load(chat.profileImageUrl)
            .transform(CircleCrop())
            .into(profileImage)

        username.text = chat.username
        lastMessage.text = chat.lastMessage
        lastMessageTime.text = chat.lastMessageTime
    }
}